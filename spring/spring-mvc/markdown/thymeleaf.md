# 타임리프

## 특징
- 서버 사이드 렌더링
- 네츄럴 템플릿
- 스프링 통합 지원

### 서버 사이드 랜더링
백엔드 서버에서 HTML을 동적으로 렌더링 하는 용도로 사용

### 네츄럴 템플릿
- 순수 HTML을 최대한 유지한다.
- 파일을 직접 열어도 확인가능하고 서버를 통해 뷰 템플릿을 거치면 동적으로 변경된 결과를 확인할 수 있다.
- 순수 HTML을 그대로 유지하면서 뷰 템플릿도 사용 가능함

## 텍스트 - text, utext
HTML의 콘텐츠에 데이터 출력하고자 할 때 `th:text=${data}` 이런식으로 사용

HTML 콘텐츠 영역 안에서 직접 데이터를 출력하고자 한다면 [[...]]를 사용 `[[${data}]]`

~~~html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <h1>컨텐츠에 데이터 출력하기</h1>

    <ul>
        <li>th:text 사용 <span th:text="${data}"></span></li>
        <li>컨텐츠 안에서 직접 출력하기 = [[${data}]]</li>
    </ul>
</body>
</html>
~~~
데이터에 `<b>hi yo</b>` 가 들어가 있을 때 위의 HTML의 결과를 확인해보면 `hi yo` 부분이 진해져야 하는데 `<b>hi yo</b>` 태그가 함께 나온다. 소스보기에서는 <, > 가 `&lt;`, `&gt;` 이렇게 나오는 것을 확인할 수 있다. 

HTML에서는 < 를 태그의 시작으로 인식한다. < 를 문자 그 자체로 표현하는 방법이 있는데 HTML 엔티티라 한다. HTML에서 사용하는 특수문자를 HTML 엔티티로 변경하는 것을 이스케이프라고 한다.

`th:text`, `[[...]]` 는 기본적으로 이스케이프를 지원한다.

이스케이프를 사용하지 않으려면

`th:utext`, `[(...)]` 처럼 사용하면 된다.

~~~html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>text vs utext</h1>
<ul>
    <li>th:text = <span th:text="${data}"></span></li>
    <li>th:utext = <span th:utext="${data}"></span></li>
</ul>
<h1><span th:inline="none">[[...]] vs [(...)]</span></h1>
<ul>
    <li><span th:inline="none">[[...]] = </span>[[${data}]]</li>
    <li><span th:inline="none">[(...)] = </span>[(${data})]</li>
</ul>
</body>
</html>
~~~

- `th:inline="none"`: 타임리프는 [[...]] 를 해석해버리기 때문에 화면에 [[...]]를 타임리프가 해석하지 말라는 옵션이다.

## 변수 - SpringEL
표현식: `${...}`
~~~java
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>SpringEL 표현식</h1>
<ul>Object
    <li>${user.username} = <span th:text="${user.username}"></span></li>
    <li>${user.age} = <span th:text="${user.age}"></span></li>
    <li>${user['username']} = <span th:text="${user['username']}"></span></li>
    <li>${user.getUsername()} = <span th:text="${user.getUsername()}"></span></li>
</ul>
<ul>List
    <li>${users[0].username}    = <span th:text="${users[0].username}"></span></li>
    <li>${users[0].age}    = <span th:text="${users[0].age}"></span></li>
    <li>${users[1].username}    = <span th:text="${users[1].username}"></span></li>
    <li>${users[1].age}    = <span th:text="${users[1].age}"></span></li>
    <li>${users[0]['username']} = <span th:text="${users[0]['username']}"></span></li>
    <li>${users[0].getUsername()} = <span th:text="${users[0].getUsername()}"></span></li>
</ul>
<ul>Map
    <li>${userMap['userA'].username} =  <span th:text="${userMap['userA'].username}"></span></li>
    <li>${userMap['userA'].age} =  <span th:text="${userMap['userA'].age}"></span></li>
    <li>${userMap['userB'].username} =  <span th:text="${userMap['userB'].username}"></span></li>
    <li>${userMap['userB'].age} =  <span th:text="${userMap['userB'].age}"></span></li>
    <li>${userMap['userA']['username']} = <span th:text="${userMap['userA']['username']}"></span></li>
    <li>${userMap['userA'].getUsername()} = <span th:text="${userMap['userA'].getUsername()}"></span></li>
</ul>

<h1>지역 변수 - (th:with)</h1>
<div th:with="first=${users[0]}, second=${users[1]}">
    <p>처음 사람의 이름은 <span th:text="${first.username}"></span></p>
    <p>처음 사람의 나이는 <span th:text="${first.age}"></span></p>
    <p>두번째 사람의 이름은 <span th:text="${second.username}"></span></p>
    <p>두번째 사람의 나이는 <span th:text="${second.age}"></span></p>
</div>

</body>
</html>
~~~

`th:with` 사용 시 지역 변수를 선언해서 사용 가능, 태그 안에서만 사용 가능 

## 자주 사용하는 객체
~~~java
@GetMapping("/basic-objects")
public String basicObjects(
        HttpSession session,
        HttpServletRequest request,
        HttpServletResponse response,
        Model model) {
    session.setAttribute("sessionData", "Hi My Session");
    model.addAttribute("request", request);
    model.addAttribute("response", response);
    model.addAttribute("servletContext", request.getServletContext());
    return "basic/basic-objects";
}

@Component("testBean")
static class TestBean {
    public String hi(String data) {
        return "hi " + data;
    }
}
~~~

~~~html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <h1>식 기본 객체 (Expression Basic Objects)</h1>

    <ul>
        <li>request = <span th:text="${request}"></span></li>
        <li>response = <span th:text="${response}"></span></li>
        <li>session = <span th:text="${session}"></span></li>
        <li>servletContext = <span th:text="${servletContext}"></span></li>
        <li>locale = <span th:text="${#locale}"></span></li>
    </ul>

    <h1>편의 객체</h1>
    <ul>
        <li>Request Parameter = <span th:text="${param.paramData}"></span></li>
        <li>session = <span th:text="${session.sessionData}"></span></li>
        <li>spring bean = <span th:text="${@testBean.hi('Spring!')}"></span></li>
    </ul>
</body>
</html>
~~~

## 유틸리티 객체와 날짜
- [타임리프 유틸리티 객체](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#expression-utility-objects)

## URL 링크
URL을 생성하고자 할 때 - `@{...}` 사용
~~~html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>URL 링크</h1> <ul>
    <li><a th:href="@{/hello}">basic url</a></li>
    <li><a th:href="@{/hello(param1=${param1}, param2=${param2})}">hello query param</a></li>
    <li><a th:href="@{/hello/{param1}/{param2}(param1=${param1}, param2=${param2})}">path variable</a></li>
    <li><a th:href="@{/hello/{param1}(param1=${param1}, param2=${param2})}">path variable + query parameter</a></li>
</ul>
</body>
</html>
~~~

쿼리 파라미터
- `@{/hello(param1=${param1}, param2=${param2})}`
  - `/hello?param1=data1&param2=data2`
  - () 안에 있는 부분이 쿼리 파라미터로 처리된다.

경로변수
- `@{/hello/{param1}/{param2}(param1=${param1}, param2=${param2})}`
  - `/hello/data1/data2`
  - URL 경로상에 변수가 있으면 () 부분은 경로 변수로 처리

경로변수 + 쿼리 파라미터
- `@{/hello/{param1}(param1=${param1}, param2=${param2})}`
  - `/hello/data1?param2=data2`

[참고](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#link-urls)

## 리터럴
타임리프는 `문자`, `숫자`, `불린`, `null` 리터럴이 있다. <br>
타임리프에서는 문자 리터럴은 항상 '' 작은 따옴표로 감싸야 한다.<br>
하지만 공백없이 쭉 이어진다면 토큰으로 인지해 작은따옴표를 생략할 수 있다.<br>

`th:text="hello world!"` 이것은 오류가 난다 "" 안에서 ''로 감싸줘야한다.
~~~html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body> <h1>리터럴</h1> <ul>
    <!--주석을 풀면 예외 발생-->
<!--    <li>"hello world!" = <span th:text="hello world!"></span></li>-->
    <li>'hello' + ' world!' = <span th:text="'hello' + ' world!'"></span></li>
    <li>'hello world!' = <span th:text="'hello world!'"></span></li>
    <li>'hello ' + ${data} = <span th:text="'hello ' + ${data}"></span></li>
    <li>리터럴 대체 |hello ${data}| = <span th:text="|hello ${data}|"></span></li>
</ul>
</body>
</html>
~~~

## 연산
~~~html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<ul>
    <li>산술 연산
        <ul>
            <li>10 + 2 = <span th:text="10 + 2"></span></li>
            <li>10 % 2 == 0 = <span th:text="10 % 2 == 0"></span></li>
        </ul>
    </li>
    <li>비교 연산
        <ul>
            <li>1 > 10 = <span th:text="1 &gt; 10"></span></li>
            <li>1 gt 10 = <span th:text="1 gt 10"></span></li>
            <li>1 >= 10 = <span th:text="1 >= 10"></span></li>
            <li>1 ge 10 = <span th:text="1 ge 10"></span></li>
            <li>1 == 10 = <span th:text="1 == 10"></span></li>
            <li>1 != 10 = <span th:text="1 != 10"></span></li>
        </ul>
    </li>
    <li>조건식
        <ul>
            <li>(10 % 2 == 0)? '짝수':'홀수' = <span th:text="(10 % 2 == 0)? '짝 수':'홀수'"></span></li>
        </ul>
    </li>
    <li>Elvis 연산자
        <ul>
            <li>${data}?: '데이터가 없습니다.' = <span th:text="${data}?: '데이터가 없습니다.'"></span></li>
            <li>${nullData}?: '데이터가 없습니다.' = <span th:text="${nullData}?: '데이터가 없습니다.'"></span></li>
        </ul>
    </li>
    <li>No-Operation
        <ul>
            <li>${data}?: _ = <span th:text="${data}?: _">데이터가 없습니다.</span></li>
            <li>${nullData}?: _ = <span th:text="${nullData}?: _">데이터가 없습니다.</span></li>
        </ul>
    </li>
</ul>
</body>
</html>

~~~

- Elvis 연산자: 조건식 편의 버전
- No-Operation: `_` 인 경우 타임리프가 실행되지 않은것처럼 동작


## 속성 값 설정
`th:*`로 속성을 지정하는 방식으로 동작함
~~~html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>속성 설정</h1>
<input type="text" name="mock" th:name="userA" />
<h1>속성 추가</h1>
- th:attrappend = <input type="text" class="text" th:attrappend="class=' large'" /><br/>
- th:attrprepend = <input type="text" class="text" th:attrprepend="class='large '" /><br/>
- th:classappend = <input type="text" class="text" th:classappend="large" /><br/>

<h1>checked 처리</h1>
- checked o <input type="checkbox" name="active" th:checked="true" /><br/>
- checked x <input type="checkbox" name="active" th:checked="false" /><br/>
- checked=false <input type="checkbox" name="active" checked="false" /><br/>
</body>
</html>
  
~~~

- `<input type="text" name="mock" th:name="userA" />`
  - 태그의 기존 속성을 `th:*`로 지정한 속성으로 대체
  - 기존 속성이 없으면 새로 만듦
  - 타임리프 랜더링 후 `<input type="text" name="userA" />`

속성 추가
- th:attrappend: 속성 값 뒤에 값을 추가
- th:attrprepend: 속성 값 앞에 값을 추가
- th:classappend: class 속성에 추가

checked
- HTML에서는 속성으로 checked가 들어가있을때 checked의 값으로 어떠한 것이 오더라도 체크 처리가 됨
- 타임리프에서는 `th:checked=false` 시 checked 속성을 태그에서 지워버린다.

## 반복
~~~html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>기본 테이블</h1> <table border="1">
    <tr>
        <th>username</th>
        <th>age</th>
    </tr>
    <tr th:each="user : ${users}">
        <td th:text="${user.username}">username</td>
        <td th:text="${user.age}">0</td>
    </tr>
</table>
<h1>반복 상태 유지</h1>
<table border="1">
    <tr>
        <th>count</th>
        <th>username</th>
        <th>age</th>
        <th>etc</th>
    </tr>
    <tr th:each="user, userStat : ${users}">
        <td th:text="${userStat.count}">username</td>
        <td th:text="${user.username}">username</td>
        <td th:text="${user.age}">0</td>
        <td>
            index = <span th:text="${userStat.index}"></span>
            count = <span th:text="${userStat.count}"></span>
            size = <span th:text="${userStat.size}"></span>
            even? = <span th:text="${userStat.even}"></span>
            odd? = <span th:text="${userStat.odd}"></span>
            first? = <span th:text="${userStat.first}"></span>
            last? = <span th:text="${userStat.last}"></span>
            current = <span th:text="${userStat.current}"></span>
        </td>
    </tr>
</table>
</body>
</html>
~~~
- `th:each="user : ${users}"`: 반복 시 `${users}` 값을 하나씩 꺼내 `user`에 담아 태그를 반복 실행
- List, 배열, Map 등에서 사용 가능
- Map에서 사용할 경우 변수에 담기는 값은 Map.Entry

반복상태<br>
`th:each="user, userStat : ${users}"`: 두번째 파라미터에 `userStat`과 같이 설정하여 반복 상태를 확인할 수 있음
- 두번째 파라미터 생략 가능, 이 때 지정한 `변수명 + Stat`가 된다

## 조건
`if`, `unless`

~~~html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>if, unless</h1>
<table border="1">
    <tr>
        <th>count</th>
        <th>username</th>
        <th>age</th>
    </tr>
    <tr th:each="user, userStat : ${users}">
        <td th:text="${userStat.count}">1</td>
        <td th:text="${user.username}">username</td>
        <td>
            <span th:text="${user.age}">0</span>
            <span th:text="'미성년자'" th:if="${user.age lt 20}"></span>
            <span th:text="'미성년자'" th:unless="${user.age ge 20}"></span>
        </td>
    </tr>
</table>
<h1>switch</h1>
<table border="1">
    <tr>
        <th>count</th>
        <th>username</th>
        <th>age</th>
    </tr>
    <tr th:each="user, userStat : ${users}">
        <td th:text="${userStat.count}">1</td>
        <td th:text="${user.username}">username</td>
        <td th:switch="${user.age}">
            <span th:case="10">10살</span>
            <span th:case="20">20살</span>
            <span th:case="*">기타</span>
        </td>
    </tr>
</table>
</body>
</html>
~~~
- `if`, `unless`: 조건에 맞지 않으면 그 태그 렌더링 안해버림
- `switch`: `*`은 만족하는 조건이 없을 때 사용하는 디폴트

## 주석
~~~html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <h1>예시</h1>
    <span th:text="${data}">html data</span>

    <h1>1. 표준 HTML 주석</h1>
    <!--
        <span th:text="${data}">html data</span>
    -->

    <h1>2. 타임리프 파서 주석</h1>
    <!--/* [[${data}]] */-->

    <!--/*-->
    <span th:text="${data}">html data</span>
    <!--*/-->

    <h1>3. 타임리프 프로토타입 주석</h1>
    <!--/*/
        <span th:text="${data}">html data</span>
        /*/-->
</body>
</html>
~~~

1. 표준 HTML 주석
- 타임리프가 렌더링 하지 않고 남겨둠
2. 타임리프 파서 주석
- 렌더링에서 주석 부분 제거
3. 타임리프 프로토 타입 주석
- HTML 파일을 브라우저에서 열어보면 웹 브라우저가 렌더링 하지 않음
- 타임리프 렌더링 시 정상 렌더링