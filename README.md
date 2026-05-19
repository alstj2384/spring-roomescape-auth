# 방 탈출 예약 시스템

## 실행 가이드

> Docker가 설치되어 있어야 합니다.

```bash
docker-compose up
```

| 서비스   | URL                   |
|-------|-----------------------|
| 백엔드   | http://localhost:8080 |
| 프론트엔드 | http://localhost:4173 |

- 백엔드는 레포지터리 소스를 기반으로 직접 빌드됩니다.
- 프론트엔드는 Docker Hub(`alstj2384/roomescape-frontend:latest`)에서 이미지를 pull합니다!

---

## 기능 목록

### 로그인
- [ ] 사용자는 회원가입을 할 수 있다.
  - [ ] 이름, 아이디, 비밀번호를 입력해야 한다.
- [ ] 사용자는 로그인할 수 있다.

### 테마

- [x] 관리자가 테마를 추가/삭제할 수 있다.
- [x] 테마 내역은 이름, 설명, 썸네일 이미지 URL을 갖는다.
- [x] 예약 내역에는 테마 정보가 포함된다.

        공포:   11:00 12:00 13:00 … 17:00
        코미디: 11:00 12:00 13:00 … 17:00
        로맨스: 11:00 12:00            -> X 모두 같은 시간을 공유해야 함

### 예약 생성

- [ ] 로그인한 사용자는 자신의 정보로 예약을 만들 수 있다.
- [ ] 테마가 다르다면 동일한 시간에 동일한 사용자가 예약을 할 수 있다.
- [ ] 로그인을 하지 않은 사용자는 예약을 생성할 수 없다.
- [x] 같은 날짜+시간+테마에 이미 예약이 있으면 예약을 거부한다.
- [x] 지나간 날짜 및 시간에 대한 예약 생성은 불가능하다.

### 예약 조회
- [ ] 로그인한 사용자는 자신의 예약을 조회할 수 있다.
- [ ] 로그인하지 않은 사용자는 인증이 필요한 예약 조회 기능을 사용할 수 없다.
- [x] 사용자가 날짜와 테마를 선택하면 예약 가능한 시간 목록이 표시된다.
  - [x] 예약 가능한 시간이란 이전 예약 리스트에 동일한 날짜 + 테마의 예약이 없는 시간이다.

### 예약 변경
- [x] 사용자는 본인의 예약 날짜/시간을 변경할 수 있다.
  - [x] 지나간 날짜 및 시간에 대한 예약 수정은 불가능하다.

### 예약 삭제
- [ ] 예약이 존재하는 시간을 삭제할 수 없다.
- [x] 지나간 날짜 및 시간에 대한 예약 삭제는 불가능하다.

### 에러 응답

- [x] 서비스 정책 위반, 유효하지 않은 입력, 존재하지 않는 리소스 등에 대해 에러 응답을 반환한다.
- [x] 500(서버 에러)는 사용자에게 노출되지 않도록 한다.
- [x] 에러 응답 본문에는 다음의 정보를 담는다. (서버 - 프론트 응답)
    - [x] 발생 시간, 메시지(상황 설명, 발생 이유, 해결책을 포함)

### 인기 테마 조회

- [x] 최근 1주 동안 예약이 많았던 테마 상위 10개를 조회할 수 있다.
- [x] 조회 기준은 오늘을 제외한 이전 7일을 기준으로 한다.

### 인증 공통 처리
- [ ] 로그인 여부 확인 로직을 컨트롤러마다 반복하지 않는다.
- [ ] 인증이 필요한 API와 필요하지 않은 API를 구분한다.
- [ ] 인증 실패 시 일관된 응답을 반환한다.

### 구현 관련

- [x] `data.sql`에 기본 시간을 추가해둔다.

---

## API 명세

### 사용자 API
|  **기능**  |       **메서드 / URL**       | **인증** |                     **요청 본문**                     |                    **쿼리 파라미터**                     |                                       **응답**                                       |
|:--------:|:-------------------------:|:------:|:-------------------------------------------------:|:--------------------------------------------------:|:----------------------------------------------------------------------------------:|
| 예약 목록 조회 |     GET /reservations     |   필요   |                         -                         |             reservationName - optional             |       200 OK <br> [{reservationId, reservationName, date, theme, time}, ...]       |
| 예약 단건 조회 |  GET /reservations/{id}   |   필요   |                         -                         |                         -                          |          200 OK <br> {reservationId, reservationName, date, theme, time}           |
|  예약 추가   |    POST /reservations     |   필요   |     {reservationName, themeId, date, timeId}      |                         -                          | 201 Created <br> {reservationId, reservationName, theme: {...}, date, time: {...}} |
|  예약 취소   | DELETE /reservations/{id} |   필요   |                         -                         |                         -                          |   200 OK <br> {reservationId, reservationName, theme: {...}, date, time: {...}}    |
|  예약 변경   |  PUT /reservations/{id}   |   필요   | {reservationName, theme: {...}, date, time: {...} |                         -                          |   200 OK <br> {reservationId, reservationName, theme: {...}, date, time: {...}}    |
|  시간 조회   |        GET /times         |  불필요   |                         -                         |                         -                          |                        200 OK <br> [{timeId, startAt}, ...]                        |
|  시간 조회   |   GET /times/available    |  불필요   |                                 -                 |                   date, themeId                    |                        200 OK <br> [{timeId, startAt}, ...]                        |
| 테마 목록 조회 |        GET /themes        |  불필요   |                         -                         |                         -                          |          200 OK <br> [{themeId, reservationName, description, url}, ...]           |
| 테마 단건 조회 |     GET /themes/{id}      |  불필요   |                         -                         |                         -                          |              200 OK <br> {themeId, reservationName, description, url}              |
| 인기 테마 조회 |    GET /themes/famous     |  불필요   |                         -                         | days - optional, date - optional, limit - optional |         200 OK <br> [{themeId, reservationName, description, url}, ...]            |
### 어드민 API
| **기능** |       **메서드 / URL**       | **인증** |              **요청 본문**              | **쿼리 파라미터** |                            **응답**                             |
|:------:|:-------------------------:|:------:|:-----------------------------------:|:-----------:|:-------------------------------------------------------------:|
| 시간 추가  |     POST /admin/times     |  필요    |             {startAt}               |     -       |       201 Created <br> {timeId, startAt}                      |
| 시간 삭제  | DELETE /admin/times/{id}  |   필요   |                  -                  |      -      |                            200 OK                             |
| 테마 생성  |    POST /admin/themes     |   필요   | {reservationName, description, url} |      -      | 201 Created <br> {themeId, reservationName, description, url} |
| 테마 삭제  | DELETE /admin/themes/{id} |   필요   |                  -                  |      -      |                            200 OK                             |


## 에러 응답 명세

### 상태 코드 분류 기준

| 상태 코드                      | 의미                | 사용 예시                               |
|----------------------------|-------------------|-------------------------------------|
| `400 Bad Request`          | 요청 형식 또는 필수값이 잘못됨 | 필수 필드 누락, 타입 불일치                    |
| `401 Unauthorized`         | 인증 실패             | 로그인 실패                              |
| `403 Forbidden`            | 권한 없음             | 사용자가 관리자 기능 수행, 다른 사용자의 예약 변경       |
| `404 Not Found`            | 요청한 리소스가 존재하지 않음  | 존재하지 않는 예약 ID 조회                    |
| `409 Conflict`             | DB 제약조건 위반        | 중복 예약, 참조되고 있는 리소스 삭제               |
| `422 Unprocessable Entity` | 비즈니스 규칙 위반        | 과거 날짜 예약, 운영 시간 외 예약                |

### 응답 본문 형식

```json
{
  "timestamp": "2026-05-03T12:00:21",
  "message": "에러 메시지"
}
```
