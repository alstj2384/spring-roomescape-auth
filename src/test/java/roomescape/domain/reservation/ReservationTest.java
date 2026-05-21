package roomescape.domain.reservation;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import roomescape.domain.Store;
import roomescape.domain.member.Member;
import roomescape.domain.member.Role;
import roomescape.domain.theme.Theme;
import roomescape.domain.theme.ThemeName;
import roomescape.domain.theme.ThumbnailUrl;

public class ReservationTest {
    @ParameterizedTest
    @MethodSource("nullCases")
    void 매개변수에_NULL이_포함되면_예외가_발생한다(Member member, ReservationDate date, ReservationTime time,
                                   Theme theme) {
        Store store = new Store(1L, "테스트", Member.create("m", "id", "pw", Role.MANAGER));
        assertThatThrownBy(() -> Reservation.reserve(member, date, time, theme, store, LocalDateTime.MIN))
                .isInstanceOf(NullPointerException.class);
    }

    static Stream<Arguments> nullCases() {
        Member member = Member.create("zeze", "id", "password", Role.CUSTOMER);
        ReservationDate date = new ReservationDate(LocalDate.of(2099, 1, 1));
        ReservationTime time = ReservationTime.of(1L, LocalTime.of(10, 0));
        Theme theme = Theme.load(
                1L,
                new ThemeName("공포의 방"),
                "설명",
                new ThumbnailUrl("https://zeze.com/thumb.jpg")
        );

        return Stream.of(
                Arguments.of(null, date, time, theme),
                Arguments.of(member, null, time, theme),
                Arguments.of(member, date, null, theme),
                Arguments.of(member, date, time, null)
        );
    }
}
