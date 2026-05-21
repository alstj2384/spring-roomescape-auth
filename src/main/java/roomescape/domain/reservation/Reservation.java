package roomescape.domain.reservation;

import java.time.LocalDateTime;
import java.util.Objects;
import roomescape.common.exception.ErrorCode;
import roomescape.common.exception.RoomEscapeException;
import roomescape.domain.Store;
import roomescape.domain.member.Member;
import roomescape.domain.theme.Theme;

public class Reservation {
    private final long id;
    private final Member member;
    private final ReservationDate date;
    private final ReservationTime time;
    private final Theme theme;
    private final Store store;

    public Reservation(long id, Member member, ReservationDate date, ReservationTime time, Theme theme, Store store) {
        this.id = id;
        this.member = member;
        this.date = date;
        this.time = time;
        this.theme = theme;
        this.store = store;
    }

    public static Reservation load(long id, Member member, ReservationDate date, ReservationTime time,
                                   Theme theme, Store store) {
        return new Reservation(id, member, date, time, theme, store);
    }

    public static Reservation reserve(Member member, ReservationDate date, ReservationTime time,
                                      Theme theme, Store store, LocalDateTime now) {
        Objects.requireNonNull(member);
        Objects.requireNonNull(date);
        Objects.requireNonNull(time);
        Objects.requireNonNull(theme);
        Objects.requireNonNull(store);
        Objects.requireNonNull(now);
        Reservation reservation = new Reservation(0L, member, date, time, theme, store);
        reservation.ensureNotPast(now);
        return reservation;
    }

    public void ensureNotPast(LocalDateTime now) {
        LocalDateTime requestDateTime = LocalDateTime.of(date.getDate(), time.getStartAt());

        if (requestDateTime.isBefore(now)) {
            throw new RoomEscapeException(ErrorCode.PAST_RESERVATION_NOT_ALLOWED);
        }
    }

    public long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public ReservationDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }

    public Theme getTheme() {
        return theme;
    }

    public Store getStore() {
        return store;
    }
}
