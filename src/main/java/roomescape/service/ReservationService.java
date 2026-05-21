package roomescape.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.common.exception.ErrorCode;
import roomescape.common.exception.RoomEscapeException;
import roomescape.controller.dto.request.ReservationCreateRequest;
import roomescape.controller.dto.request.ReservationUpdateRequest;
import roomescape.domain.Store;
import roomescape.domain.member.Member;
import roomescape.domain.member.Role;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationDate;
import roomescape.domain.reservation.ReservationTime;
import roomescape.domain.theme.Theme;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.repository.StoreRepository;
import roomescape.repository.ThemeRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;
    private final ThemeRepository themeRepository;
    private final StoreRepository storeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository, ThemeRepository themeRepository,
                              StoreRepository storeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
        this.themeRepository = themeRepository;
        this.storeRepository = storeRepository;
    }

    @Transactional
    public Reservation reserve(ReservationCreateRequest request, Member member, LocalDateTime now) {
        ReservationTime reservationTime = findReservationTimeByTimeId(request.getTimeId());
        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new RoomEscapeException(ErrorCode.STORE_NOT_FOUND));
        Theme theme = findThemeByThemeId(request.getThemeId());

        Reservation reservation = Reservation.reserve(member, new ReservationDate(request.getDate()), reservationTime,
                theme, store, now);

        validateIsDuplicateReservation(request.getTimeId(), request.getThemeId(), request.getDate());

        return reservationRepository.save(reservation);
    }

    public Reservation find(long reservationId, Member member) {
        Reservation reservation = findReservationById(reservationId);
        validateAuthority(member, reservation);

        return reservation;
    }

    private void validateAuthority(Member member, Reservation reservation) {
        if (member.getRole() == Role.CUSTOMER) {
            Store store = storeRepository.findByMemberId(member.getId())
                    .orElseThrow(() -> new RoomEscapeException(ErrorCode.STORE_NOT_FOUND));

            if (store.getId() != reservation.getStore().getId()) {
                throw new RoomEscapeException(ErrorCode.UNAUTHORIZED);
            }
        }

        if (member.getRole() == Role.MANAGER) {
            if (member.getId() == reservation.getMember().getId()) {
                throw new RoomEscapeException(ErrorCode.UNAUTHORIZED);
            }
        }
    }

    public List<Reservation> findList(String name) {
        if (name != null) {
            return reservationRepository.findAllByName(name);
        }
        return reservationRepository.findAll();
    }

    public List<Reservation> findList(Member member) {
        if (member == null) {
            throw new RoomEscapeException(ErrorCode.MEMBER_NOT_FOUND);
        }
        return reservationRepository.findAllByMemberId(member.getId());
    }

    @Transactional
    public Reservation update(ReservationUpdateRequest request, Member member, long id, LocalDateTime now) {
        Reservation reservation = findReservationById(id);
        validateAuthority(member, reservation);

        Store store = storeRepository.findById(reservation.getStore().getId())
                .orElseThrow(() -> new RoomEscapeException(ErrorCode.STORE_NOT_FOUND));

        reservation.ensureNotPast(now);

        ReservationDate reservationDate = new ReservationDate(request.getDate());
        ReservationTime reservationTime = findReservationTimeByTimeId(request.getTimeId());

        if (reservationRepository.existsByTimeAndThemeAndDateExcludeId(request.getTimeId(), request.getThemeId(),
                request.getDate(), id)) {
            throw new RoomEscapeException(ErrorCode.DUPLICATE_RESERVATION);
        }

        Reservation target = Reservation.reserve(member, reservationDate, reservationTime,
                reservation.getTheme(), store, now);
        target.ensureNotPast(now);

        return reservationRepository.update(id, target);
    }

    @Transactional
    public void cancel(long reservationId, Member member, LocalDateTime now) {
        Reservation reservation = findReservationById(reservationId);
        validateAuthority(member, reservation);

        reservation.ensureNotPast(now);

        reservationRepository.deleteById(reservationId);
    }

    private ReservationTime findReservationTimeByTimeId(long reservationTimeId) {
        return reservationTimeRepository.findById(reservationTimeId)
                .orElseThrow(() -> new RoomEscapeException(ErrorCode.RESERVATION_TIME_NOT_FOUND));
    }

    private Theme findThemeByThemeId(long themeId) {
        return themeRepository.findById(themeId).orElseThrow(
                () -> new RoomEscapeException(ErrorCode.THEME_NOT_FOUND));
    }

    private void validateIsDuplicateReservation(long timeId, long themeId, LocalDate date) {
        if (reservationRepository.existsByTimeAndThemeAndDate(timeId, themeId, date)) {
            throw new RoomEscapeException(ErrorCode.DUPLICATE_RESERVATION);
        }
    }

    private Reservation findReservationById(long reservationId) {
        return reservationRepository.findById(reservationId).orElseThrow(
                () -> new RoomEscapeException(ErrorCode.RESERVATION_NOT_FOUND));
    }
}
