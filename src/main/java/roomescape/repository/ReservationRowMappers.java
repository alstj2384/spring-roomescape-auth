package roomescape.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import roomescape.domain.Store;
import roomescape.domain.member.Member;
import roomescape.domain.member.Role;
import roomescape.domain.reservation.ReservationTime;
import roomescape.domain.theme.Theme;
import roomescape.domain.theme.ThemeName;
import roomescape.domain.theme.ThumbnailUrl;

public class ReservationRowMappers {
    private ReservationRowMappers() {
    }

    public static Member mapMember(ResultSet rs) throws SQLException {
        return Member.load(
                rs.getLong("member_id"),
                rs.getString("member_name"),
                rs.getString("member_login_id"),
                rs.getString("member_password"),
                Role.valueOf(rs.getString("member_role")));
    }

    public static ReservationTime mapTime(ResultSet rs) throws SQLException {
        return ReservationTime.of(
                rs.getLong("time_time_id"),
                rs.getTime("time_start_at").toLocalTime());
    }

    public static Theme mapTheme(ResultSet rs) throws SQLException {
        return Theme.load(
                rs.getLong("theme_theme_id"),
                new ThemeName(rs.getString("theme_theme_name")),
                rs.getString("theme_description"),
                new ThumbnailUrl(rs.getString("theme_thumbnail_url")));
    }

    public static Store mapStore(ResultSet rs) throws SQLException {
        Member storeManager = Member.load(
                rs.getLong("store_member_id"),
                rs.getString("store_member_name"),
                rs.getString("store_member_login_id"),
                rs.getString("store_member_password"),
                Role.valueOf(rs.getString("store_member_role")));
        return new Store(
                rs.getLong("theme_store_id"),
                rs.getString("theme_store_name"),
                storeManager);
    }
}