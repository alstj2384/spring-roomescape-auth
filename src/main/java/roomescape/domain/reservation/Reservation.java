package roomescape.domain.reservation;

import roomescape.common.exception.ErrorCode;
import roomescape.common.exception.RoomEscapeException;
import java.time.LocalDateTime;
import java.util.Objects;
import roomescape.domain.member.Member;
import roomescape.domain.theme.Theme;

public class Reservation {
    private final long id;
    private final Member member;
    private final ReservationDate date;
    private final ReservationTime time;
    private final Theme theme;

    private Reservation(long id, Member member, ReservationDate date, ReservationTime time,
                        Theme theme) {
        this.id = id;
        this.member = Objects.requireNonNull(member);
        this.date = Objects.requireNonNull(date);
        this.time = Objects.requireNonNull(time);
        this.theme = Objects.requireNonNull(theme);
    }

    public static Reservation load(long id, Member member, ReservationDate date, ReservationTime time,
                                   Theme theme) {
        return new Reservation(id, member, date, time, theme);
    }

    public static Reservation reserve(Member member, ReservationDate date, ReservationTime time,
                                      Theme theme, LocalDateTime now) {
        Objects.requireNonNull(now);
        Reservation reservation = new Reservation(0L, member, date, time, theme);
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
}
