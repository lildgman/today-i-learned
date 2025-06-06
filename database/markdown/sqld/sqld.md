# SQLD 정리

[SQLD 강의1](https://www.youtube.com/watch?v=xmkaOqbTQRE&list=LL&index=6)
<br>
[SQLD 강의2](https://www.youtube.com/watch?v=jx_zBkaVqjc&list=LL&index=4)

## 데이터모델링
### 데이터모델의 이해

- 컬럼: 속성
- 행: 인스턴스
- 테이블: 엔티티

#### 모델링의 개념

- 현실 세계의 `비즈니스 프로세스`와 `데이터 요구 사항`을 **추상적**이고 **구조화**된 형태로 표현하는 과정
- 데이터베이스의 `구조와 관계 정의`, 데이터베이스를 통해 데이터 **저장**, **조작**, **관리** 방법을 명확히 정의

#### 모델링의 특징

1. **단순화(Simplification)**
- 현실을 `단순화`하여 핵심 요소에 집중하고 `불필요한 세부 사항 제거`
- 단순화를 통해 복잡한 현실 세계를 이해하고 표현하기 쉬워짐

2. **추상화(Abstraction)**
- 현실세계를 일정한 형식에 맞춰 `간략하게` 대략적으로 표현하는 과정
- 다양한 현상을 일정한 양식인 표기법에 따라 표현

3. **명확화(Clarity)**
- 대상에 대한 `애매모호함을 최대한 제거`하고 `정확하게 현상을 기술`하는 과정
- 명확화를 통해 모델을 이해하는 이들의 의사소통을 원할히 함

#### 데이터 모델링 3가지 관점

1. **데이터 관점**
- `데이터`가 어떻게 **저장, 접근, 관리**되는지 정의하는 단계

2. **프로세스 관점**
- `시스템`이 어떤 `작업`을 수행하고, `작업`들이 어떻게 조직되고 조정되는지 정의하는 단계
- 데이터가 시스탬 내에서 `어떻게 흐르고 변환`되는지에 대한 확인

3. **데이터와 프로세스 관점**
- `데이터 관점`과 `프로세스 관점`을 `결합`하여 시스템의 전반적인 동적을 이해하는 단계
- `특정 프로세스가 어떤 데이터`를 사용하는지, 데이터가 어떻게 생성되고 변경되는지 명확하게 정의

#### 데이터 모델링 유의점

1. **중복(Duplication)**
- 한 테이블 또는 여러 테이블에 `같은 정보를 저장하지 않도록` 설계

2. **비유연성(Inflexibility)**
- 사소한 업무 변화에 대해서도 `잦은 모델 변경이 되지 않도록` 주의

3. **비일관성(Inconsistency)**
- 데이터베이스 내 정보가 `모순되거나 상반된 내용`을 갖는 상태를 의미
- 데이터간 `상호연관 관계`를 명확히 정의
- 데이터의 중복이 없더라고 비일관성은 발생할 수 있음
- 데이터 품질 관리 필요

#### 데이터 모델링 3가지 요소

- `대상(Entity)`: 업무가 관리하고자 하는 `대상`(객체)
- `속성(Attribute)`: 대상들이 갖는 속성(하나의 특징으로 정의될 수 있는 것)
- `관계(Relationship)`: 대상들 간의 관계

#### 데이터 모델링의 3단계

1. **개념적 모델링**
- `업무 중심적`, 포괄적인 수준의 모델링
- `추상화 수준이 가장 높음`
- 업무 분석 후 업무의 `핵심 엔티티를 추출하는 단계`
- 도출한 핵심 엔티티들과의 관계를 표현하기 위해 ERD작성

2. **논리적 모델링**
- 개념적 모델링의 결과를 토대로 `세부속성, 식별자, 관계` 등을 표현하는 단계
- `데이터 구조를 정의`하기 때문에 비슷한 업무나 프로젝트에서 동일한 형태의 데이터 사용시 `재사용 가능`
- 동일한 논리적 모델 사용 시 쿼리도 재사용 가능
- `데이터 정규화 수행`
- 재사용성 높은 논리적 모델은 유지보수가 용이

3. **물리적 모델링**
- 논리 모델링이 끝나면 이를 `직접 물리적으로 생성`하는 과정
- 데이터베이스 성능, 디스크 저장구조, 하드웨어의 보안성, 가용성 등을 고려
- 가장 구체적인 데이터 모델링
- `추상화 수준 가장 낮음`(가장 구체적)

#### 스키마의 3단계 구조
- 스키마: 데이터베이스의 구조와 제약 조건에 관한 전반적인 명세를 기술한 메타데이터의 집합
- 외부, 개념, 내부 스키마로 분리
- 사용자의 관점과 실제 설계된 물리적인 방식을 분리하기 위해 고안

1. **외부 스키마**
- `사용자가 보는 관점`에서 데이터베이스 스키마를 정의
- `사용자나 응용 프로그램이 필요한 데이터`를 정의(View: 사용자가 접근하는 대상)

2. **개념 스키마**
- 사용자 관점의 데이터베이스 스키마를 통합하여 `데이터베이스의 전체 논리적 구조를 정의`
- 전체 데이터베이스의 `개체, 속성, 관계, 데이터 타입` 등을 정의

3. **내부 스키마**
- 데이터가 `물리적으로 어떻게 저장되는지`를 정의
- 데이터의 `저장 구조, 컬럼, 인덱스` 등을 정의

##### 3단계 스키마의 독립성
- **독립성**: 물리적, 논리적 구조를 변경하더라도 사용자가 사용하는 응용 프로그램에 `영향을 주지 않는 특성`
1. **논리적 독립성**: `논리적 데이터 구조`가 변경되어도(개념 스키마 변경) `응용 프로그램`에 영향을 주지 않는 특성
2. **물리적 독립성**: `물리적 구조`가 변경되어도(내부 스키마 변경) `개념/ 외부 스키마`에 영향을 주지 않는 특성

#### 데이터 모델의 표기법(ERD: Entity Relationship Diagram)
- 엔티티와 엔티티 간의 관계를 시작적으로 표현한 다이어그램
- 1976년 피터 첸이 만듦, 데이터 모델링 표준으로 사용

#### ERD 작성 절차
1. 엔티티를 도출한 후 그린다
2. 엔티티 배치
3. 엔티티 간 관계를 설정
4. 관계명 서술
5. 관계의 참여도 기술
6. 관계의 필수 여부 확인

<hr>

### 엔티티의 개념
- 현실 세계에서 독립적으로 식별 가능한 `객체`나 `사물`
- 업무상 분석해야하는 `대상(인스턴스, 컬럼)들로 이루어진 집합`
- 인스턴스는 엔티티의 `속성 값들로 구성`, 엔티티의 개념을 `현실에서 구체적으로 나타낸 것`
<Br>

ex) 
- 엔티티: 학생
- 속성(Attribute): 학번, 이름, 학과 등
- 식별자(Identifier): 학번(고유한 학번으로 학생을 식별)
- 인스턴스: 특정 학생의 데이터
  - 학번: 19900101
  - 이름: ㅁㅁㅁ
  - 학과: 컴공

#### 엔티티의 특징

1. `유일한 식별자`에 의해 식별 가능
- 인스턴스가 식별자에 의해 한 개씩만 존재하는지 검증이 필요
- 유일한 식별자는 그 엔티티의 인스턴스만의 고유 이름
- ex) 이름은 동명이인이 있을 수 있으므로 사번, 학번 등이 고유식별자

2. 해당 업무에 `필요`하고 `관리하고자 하는 정보`
- 설계하는 업무의 시스템 구축에 필요한 정보여야함

3. `인스턴스들의 집합`
- 영속적으로 존재하는 `2개 이상의 인스턴스 집합`
- 인스턴스가 한 개 밖에 없는 엔티티는 집합이 아니므로 성립이 안됨

4. 엔티티는 `반드시 속성를 가짐`
- 각 엔티티는 `2개 이상의 속성`을 가짐
- 하나의 인스턴스는 `각각 속성들에 대한 1개의 속성값`만을 가짐

5. 엔티티는 업무 프로세스에 의해 이용
- 업무적으로 필요해 선정했음에도 실제로 `사용되지 않으면 잘못 설계된 것`
- 모델링 시 발견하기 어려운 경우 `데이터 모델 검증`이나 상관 모델링 시 `단위 프로세스 교처점검`으로 문제를 도출
- 누락된 프로세스의 경우 추후 해당 프로세스 추가
- 반대로 사용되지 않은 고립 엔티티는 제거가 필요함

6. 다른 엔티티와 `최소 1개 이상의 관계 성립`
- 엔티티는 업무적 연관성을 갖고 `다른 엔티티와 연관`의 의미를 가짐
- 관계가 없는 엔티티 도출은 부적절한 엔티티이거나 적절한 관계를 찾지 못한것

#### 엔티티의 분류

##### `유형과 무형에 따른 분류`

1. **유형엔티티**
- `물리적 형태가 있음`
- 안정적이며 지속적으로 활용되는 엔티티
- 업무로부터 구분하기가 가장 용이한 엔티티
<br>
ex) 사원, 물품 등 

2. **개념엔티티**
- `물리적인 형태 없음`
- 관리해야 할 개념적 정보로부터 구분되는 엔티티
<br>
ex) 조직, 보험상품 등

3. **사건엔티티**
- `업무를 수행함에 따라 발생하는 엔티티`
- `발생량이 많고` 각종 통계자료에 이용
<br>
ex) 주문, 청구, 미납 등

###### `발생 시점에 따른 분류`

1. **기본엔티티**
- `그 업무에 원래 존재하는 정보`
- 다른 엔티티와 관계에 의해 생성되지 않고 `독립적으로 생성`
- 다른 엔티티의 부모 역할을 하는 엔티티
- 다른 엔티티로부터 주식별자를 상속받지 않고 `자신의 고유한 주식별자를 가짐`

ex) 사원, 부서, 고객 등

2. **중심엔티티**
- 기본엔티티로부터 발생되고 `그 업무에서 중심적인 역할`
- `많은 데이터가 발생`되고 다른 엔티티와의 `관계`를 통해 많은 행위 엔티티를 생성

ex) 계약, 사고, 청구 등

3. **행위엔티티**
- `2개 이상의 부모엔티티로부터 발생`
- 자주 내용이 바뀌거나 데이터 양이 증가
- 분석 초기 단계보단 `상세 설계 단계`나 `프로세스와 상관모델링`을 진행하면서 도출 

ex) 주문(고객과 상품 엔티티로부터 발생하므로 행위엔티티이기도 하다)

#### 엔티티의 명명규칙
- 현업에서 사용하는 용어 사용
- 약자 사용 자제
- 단수 명사 사용
- 모든 엔티티에서 유일하게 이름 부여
- 엔티티 생성의 의미대로 이름 부여

<Hr>

### 속성의 개념
- 업무에서 필요로하는 `고유한 성질, 특징`을 의미 -> `컬럼`으로 표현할 수 있는 단위
- 업무상 인스턴스로 관리하고자 하는 `더 이상 분리되지 않는 최소 데이터 단위`
- 인스턴스의 구성 요소(학생 엔티티에 이름, 학번 등이 속성이 될 수 있음)

#### 엔티티, 인스턴스, 속성, 속성값의 괸계
- 한 개의 엔티티에는 `2개 이상의 인스턴스`의 집합이어야 한다(하나의 테이블은 두 개 이상의 행을 가진다)
- 한 개의 엔티티는 `2개 이상의 속성`을 갖는다. (하나의 테이블은 두 개 이상의 컬럼으로 구성된다.)
- 한 개 속성은 `1개의 속성값`을 갖는다.(각 컬럼의 값은 하나씩만 삽입 가능)
- 속성은 엔티티에 속한 엔티티에 대한 구체적인 정보를 나타낸다.

#### 속성의 특징
- 반드시 해당 업무에서 `필요하고 관리하고자 하는 정보`여야 한다.
- 정해진 `주식별자에 함수적 종속성을 가져야 한다.`
- 하나의 속성은 `한 개의 값만을` 가진다.
- 하나의 속성에 여러 개의 값이 있을 경우 별도의 엔티티를 이용해 분리해야 한다.
- 하나의 인스턴스는 `속성마다 반드시 하나의 속성값`을 가진다.
-> 각 속성이 하나의 값을 갖고있음을 의미(속성의 원자성)
- `원자성`은 데이터모델의 `각 엔티티의 인스턴스`가 해당 `속성`에 대해 `단일하고 명확한 값`을 가지는 것을 의미

#### 함수적 종속성
- 한 속성값이 다른 속성값에 종속적인 관계를 갖는 특징을 말함
- `어떤 속성 A의 값에 의해 다른 속성 B도 유일하게 결정`된다면 `B는 A에 함수적으로 종속`됐다고 한다. (A->B)

1. **완전 함수적 종속**
- 특정 컬럼이 기본키에 대해 `완전히 종속`될 때를 의미
- PK를 구성하는 컬럼이 2개 이상일 경우, PK 값 모두에 의해 종속관계를 나타낼 때 `완전 함수 종속성`을 만족
<br>
ex) 주문번호 + 제품번호 에 의해 수량 컬럼 값이 결정됨

2. **부분 함수적 종속**
- 기본키 전체가 아닌, `기본키 일부`에 대해 종속될 때를 의미
<br>
ex) 수강기록 테이블에서 `학생번호`와 `과목`이 Pk라고 가정할 때, `과목`에 의해서도 교수가 결정된다면 `부분 함수적 종속 관계`

#### 속성의 분류
##### `속성의 특성에 따른 분류`

1. **기본 속성**
- `업무로부터 추출된 모든 특성`
- 엔티티에 가장 일반적으로 많이 존재하는 속성

2. **설계 속성**
- 기본 속성 외, `업무를 규칙화`하기 위해 새로 만들어지거나 `기본 속성을 변형`하여 만들어지는 속성

3. **파생 속성**
- `다른 속성에 의해 만들어지는 속성`
- 일반적으로 계산된 값들
- 데이터 정합성을 유지하기 위해 적게 정의하는 것이 좋다.

##### `엔티티 구성방식에 따른 분류`

1. **PK(Primary Key, 기본키)**
- 인스턴스를 식별할 수 있는 속성

2. **FK(Foreign Key, 외래키)**
- 다른 엔티티와의 관계에서 포함된 속성

3. **일반 속성**
- 엔티티에 포함되어 있고 PK/FK에 포함되지 않는 속성

##### `분해 여부에 따른 속성`

1. 단일 속성
- 하나의 의미로 구성된 경우

2. 복합 속성
- 여러개의 의미로 구성된 경우

3. 다중값 속성
- 속성에 여러 개의 값을 가질 수 있는 경우
- 다중값 속성은 엔티티로 분해

#### 속성의 명명규칙
- 해당 업무에서 사용하는 이름
- 서술식 속성명 사용하지 않는다.
- 약어 사용은 제한
- 전체 데이터 모델에서 유일한 명칭

#### 도메인
- 각 속성이 가질 수 있는 값의 범위
- 엔티티 내에서 속성에 대한 데이터 타입과 크기, 제약사항을 지정하는 것

<hr>

### 관계의 개념
- `엔티티간의 연관성`을 나타낸 개념
- 관계를 정의할 때는 인스턴스간 논리적인 연관성을 파악하여 정의
- 엔티티를 어떻게 정의하느냐에 따라 변경되기도 함

#### 관계의 종류

1. **존재적 관계**
- 한 엔티티의 `존재`가 다른 엔티티의 `존재`에 영향을 미치는 관계
- ex) 부서 엔티티가 삭제되면 사원 엔티티의 존재에 영향을 미친다

2. **행위적 관계**
- `엔티티 간의 어떤 행위`가 있는 것을 의미
- ex) 고객 엔티티의 행동에 의해 주문 엔티티가 발생

erd에서는 존재관계와 행위관계를 구분하지 않는다.

#### 관계의 구성
1. 관계명
2. 차수(cardinality)
3. 선택성(Optionality)

#### 관계의 차수(Cardinality)
- 한 엔티티의 레코드(인스턴스)가 다른 엔티티의 레코드(인스턴스)와 어떻게 `연결`되는지 나타내는 표현

1. **1대1 관계**
- **완전 1대1 관계**
  - 하나의 엔티티에 관계되는 엔티티가 `반드시 하나`로 존재하는 경우
  - ex) 사원은 반드시 소속부서를 갖고 있어야 한다.
- **선택적 1대1 관계**
  - 하나의 엔티티에 관계되는 엔티티가 `하나이거나 없을 수 있는 경우`
  - ex) 사원은 하나의 부서가 있거나 발령전이면 없을 수 있다.

2. **1대N 관계**
- 엔티티에 `하나의 행`에 다른 엔티티의 값이 `여러 개` 있는 관계
- ex) 고객은 여러 계좌를 갖을 수 있다.

3. **M대 N 관계**
- 두 엔티티가 다대다 연결 관계를 갖고 있음
- 이 경우 조인 시 카테시안 곱이 발생하므로 두 엔티티를 연결하는 `연결 엔티티`를 추가로 `1대N 관계`로 해소할 필요가 있다.
- ex) 한 학생이 여러 강의를 갖고 있을 수 있고, 하나의 강의는 여러 학생을 보유할 수 있다. -> 두 엔티티를 연결해주는 구매이력 엔티티가 필요하다.

#### 관계의 페어링
- 엔티티 안에 인스턴스가 개별적으로 관계를 가지는 것
- 관계란 페어링의 집합을 의미

<br>

**관계의 차수, 페어링의 차이**
- 학생과 강의 엔티티는 관계를 가진다.
- 한 학생은 여러 강의를 수강할 수 있고, 하나의 강의도 여러 학생에게 수강될 수 있으므로 다대다 관계.
- 인스턴스의 관계를 보면 학생 A가 강의 B를 `언제 수강했고 성적은 무엇이다.` 와 같이 페어링이 형성된다.
- `차수`는 하나의 엔티티와 다른 엔티티 간 레코드 연결 방식
- `페어링`은 두 엔티티 간 특정 연결을 설명하고 추가 정보를 제공하는 용도로 사용한다.

<hr>

### 식별자
#### 식별자 개념
- 하나의 엔티티에 구성된 여러 개의 속성 중 `엔티티를 대표할 수 있는 속성`
- 하나의 `유일한 식별자`가 존재해야 함
- 논리 모델링에서는 `식별자`, 물리 모델링에서는 `키`라고 표현한다.

#### 주식별자의 특징

1. **유일성**: 주식별자에 의해 모든 인스턴스를 `유일하게 구분함`
- ex) 이름은 동명이인이 있을 수 있으므로 학생번호와 같은 유일한 식별자를 주식별자로 사용

2. **최소성**: 주식별자를 구성하는 속성은 유일성을 만족하는 `최소한의 속성`으로 구성
- ex) 학생 엔티티는 학생번호만으로도 충분한데 학생번호 + 이름 으로 구성할 필요가 없다.

3. **불변성**: 주식별자가 한번 특정 엔티티에 지정되면 그 식별자의 값은 `변하지 않아야 한다`.

4. **존재성**: 주식별자는 지정되면 `반드시 값이 존재`해야 하며 NULL은 허용하지 않는다.

#### 식별자 분류
1. **대표성 여부에 따른 식별자의 종류**
- **주식별자**(ex: `학생번호`)
  - 유일성과 최소성을 만족하면서 엔티티를 `대표`하는 식별자
  - 엔티티 내에서 `각 인스턴스를 유일하게 구분`할 수 있는 식별자
  - 타 엔티티와 `참조관계`를 연결할 수 있는 식별자

- **보조식별자** (ex: `학생 주민번호`)
  - 엔티티 내에서 각 인스턴스를 구분할 수 있는 구분자이지만, `대표성을 가지지못해` 참조 관계를 연결할 수 없는 식별자
  - 유일성과 최소성은 만족하지만, `대표성을 만족하지 못하는 식별자`

2. **생성 여부에 따른 식별자의 종류**
- 내부식별자: 다른 엔티티 참조 없이 `엔티티 내부`에서 스스로 생성되는 식별자

- 외부식별자: `다른 엔티티와 관계로 인하여` 만들어지는 식별자(외래키)

3. **속성 수에 따른 식별자 종류**
- 단일식별자: `하나의 속성`으로 구성
- 복합식별자: `2개 이상의 속성`으로 구성

4. **대체 여부에 따른 식별자**
- 본질식별자: 비즈니스 프로세스에서 만들어지는 식별자
- 인조식별자: 인위적으로 만들어지는 식별자, ex) 자동 증가하는 일련번호

#### 주식별자 도출기준
1. 해당 업무에서 `자주 이용되는 속성`을 주식별자로 지정
- 같은 식별자 조건을 만족하더라고 업무적으로 더 많이 사용되는 속성을 주식별자로 지정
2. 명칭이나 내역 등과 같은 이름은 피함
- 부서명 보다는 `부서코드`로 주식별자로 사용
3. 속성의 수를 `최대한 적게 구성`
- 주식별자를 너무 많은 속성으로 구성 시, 조인으로 인한 성능저하 발생 우려


#### 관계간 엔티티 구분
1. 강한 개체
- `독립적으로 존재`할 수 있는 엔티티
- 고객과 계좌 엔티티 중, 고객은 독립적으로 존재할 수 있다.
2. 약한 개체
- 독립적으로 존재할 수 없은 엔티티
- 고객과 계좌 엔티티 중 계좌는 독립적으로 존재할 수 없음

#### 식별 관계와 비식별관계
1. 식별관계(Identification Relationship)
- `하나의 엔티티의 기본키를 다른 엔티티가 기본키`의 하나로 공유하는 관계
- erd에서 실선으로 표현
2. 비식별관계(Non-identification Relationship)
- 강한 개체의 기본키를 다른 엔티티의 기본키가 아닌 `일반 속성`으로 관계를 가지는 것

#### 키의 종류

1. 기본키(Primary Key)
- `엔티티를 대표`할 수 있는 키

2. 후보키(Candidate key)
- 유일성(유니크)과 최소성(최소한의 컬럼으로 유일성을 만족하는)을 만족하는 키
- `후보키 중 하나가 기본키`가 되고 나머지를 `대체키`라고 한다.

3. 슈퍼키(Super key)
- 유일성은 만족하지만 `최소성은 만족하지 않는 키`
- ex) 학생 테이블에서 학번만으로 pk를 구성할 수 있지만 (학번+이름)으로 구성한다면 이는 슈퍼키

4. 대체키(Alternate Key)
- 여러 후보키 중 기본키가 아닌 키

5. 외래키(Foreign key)
- 다른 테이블의 기본키를 참조하는 키
- 참조 테이블은 하나 또는 여러 개 가능

<hr>

### 정규화
#### 정규화의 개념
- `최소한의 데이터`만을 하나의 엔티티에 넣는식으로 데이터를 분해하는 과정
- 데이터의 일관성, 최소한의 데이터 중복, 최대한의 데이터 유연성을 위한 과정
- 데이터의 `중복을 제거`, `데이터 모델의 독립성`을 확보
- 데이터 이상현상을 줄이기 위한 데이터베이스 설계 기법
- 엔티티를 상세화하는 과정, `논리 모델링 수행 시점`에서 고려됨

#### 이상현상(Abnormality)
- 정규화를 하지 않아 발생하는 현상(삽입이상, 갱신이상, 삭제이상)
- 삽입이상: 특정 인스턴스가 삽입될 때 정의되지 않아도 될 속성까지도 입력되어야하는 현상
- 삭제이상: 부서정보만 삭제하면 되는데 관련 사원 정보까지도 함께 삭제되는 현상

#### `정규화 단계`
1. **제 1 정규화(1NF)**
- 테이블이 컬럼이 `원자성`(한 속성이 하나의 값을 갖는 특성)을 갖도록 테이블을 분해하는 단계
- 하나의 행과 컬럼의 값이 `반드시 한 값`만 입력되도록 행을 분리하는 단계

2. **제 2 정규화(2NF)**
- 제 1 정규화를 진행한 테이블에 대해서 `완전 함수 종속`을 만들도록 테이블을 분해
- `완전 함수 종속`: 기본키를 구성하는 모든 컬럼의 값이 다른 컬럼을 결정짓는 상태
- PK가 2개 이상일 때 발생, `PK의 일부와 종속되는 관계가 있다면 분리`

3. **제 3 정규화(3NF)**
- 제 2 정규화를 진행한 테이블에 대해 `이행적 종속`을 없애도록 테이블을 분리
- `이행적 종속`: A->B, B->C의 관계가 성립한다고 할 때, A->C가 성립되는 것을 말함
- (A,B)와 (B,C)로 분리하는 것이 제 3 정규화

4. BCNF(Boyce-Codd Normal Form) 정규화
- 모든 결정자가 후보키가 되도록 테이블을 분해하는 것

5. 제 4 정규화
- 여러 컬럼들이 하나의 컬럼을 종속시키는 경우 분해하여 다중값 종속성을 제거

6. 제 5 정규화
- 조인에 의해 종속성이 발생되는 경우 분해

#### 반정규화(역정규화, De-Normalization)
- 데이터베이스의 성능 향상을 위해 데이터 중복을 허용하고 조인을 줄이는 데이터베이스 성능 향상 기법
- 정규화된 데이터 모델을 중복, 통합, 분리하는 데이터 모델링 기법
- 조회 속도를 향상시키지만, 데이터 모델의 유연성은 낮아진다.

#### 반정규화 수행 케이스
- 수행속도가 느려지는 경우
- 다량의 범위를 자주 처리해야 하는 경우
- 특정 범위의 데이터만 자주 처리하는 경우
- 요약/집계 정보가 자주 요구되는 경우

<hr>

### 관계
#### 관계의 개념
- 엔티티의 인스턴스 사이의 `논리적인 연관성`
- 부모의 식별자를 자식에 상속하고, 상속된 속성을 매핑키(조인키)로 활용

#### 관계의 분류
- 존재 관계: 엔티티 간의 상태
- 행위 관계: 엔티티 간 어떤 행위가 있는 것

#### 조인의 의미
- 두 테이블 사이 데이터를 출력하거나 참조하기 위해서 데이터를 연결하는 과정

#### 계층형 데이터 모델
- 자기 자신끼리 관계가 발생(셀프조인)
- 하나의 엔티티 내의 인스턴스끼리 계층 구조를 가지는 경우

#### 상호배타적 관계
- 두 테이블 중 하나만 가능한 관계

<hr>

### 트랜잭션
#### 트랜잭션이란
- ` 하나의 연속적인 업무 단위`
- 트랜잭션에 의한 관계는 `필수적인 관계 형태`를 가진다.
- 하나의 트랜잭션에는 select, insert, delete, update 등이 포함될 수 있다.

#### 필수적, 선택전 관계와 ERD
- 두 엔티티의 관계가 서로 필수적일 때 하나의 트랜잭션 형성
- 두 엔티티가 서로 독립적 수행이 가능하다면 선택적 관계로 표현
<br>

IE 표기법
- `원`을 사용해 필수적 관계와 선택적 관계 구분
- `필수적 관계`에는 원을 그리지 않는다.
- `선택전 관계`에는 관계선 끝에 원을 그려준다.
<br>

바커표기법
- `필수적 관계`: 관계선을 실선
- `선택적 관계`: 관계선을 점선

<hr>

### NULL
#### NULL이란
- DBMS에서 `아직 정해지지 않은 값`을 의미
- 0과 빈문자열("")과 다른 개념

#### NULL의 특성
1. NULL을 포함한 연산 결과는 항상 NULL

2. 그룹함수는 NULL을 제외하고 연산을 수행한다.
- SUM, AVG, MIN, MAX등 그룹 함수는 `항상 NULL을 무시`하고 연산
- COUNT는 NULL이 아닌 값의 수만 세지만 COUNT(*)는 NULL과 상관없이 모든 행의 수를 리턴

#### NULL의 ERD 표기범
- IE 표기법에서는 NULL 허용여부 확인 불가능
- 바커표기법에서는 속성 앞에 동그라미

### 식별자 구분
#### 대체 여부에 따른 식별자 구분
1. 본질식별자
- 업무에 의해 만들어지는 식별자(꼭 필요한 식별자)

2. 인조식별자
- 인위적으로 만들어지는 식별자(꼭 필요하지 않지만 편이성 등의 이유로 인위적으로 만들어지는 식별자)
- 본질식별자가 복잡한 구성을 가질 때 인위적으로 생성
- 주로 각 행을 구분하기 위핸 기본키로 사용, 자동으로 증가하는 일련번호 같은 형태
- 단점
  - `중복 데이터` 발생 가능성
  - `불필요한 인덱스` 생성

<hr>

## SQL 기본 및 활용
### 관계형 데이터베이스 개요
#### 데이터베이스 vs DBMS
- 데이터베이스: 데이터의 집합, 형식을 갖추지 않아도 엑셀 파일 등을 모아둔다면 그것 또한 데이터베이스이다.
- DBMS: 데이터를 효과적으로 관리하기 위한 시스템

#### 관계형 데이터베이스 구성 요소
- 계정: 데이터 접근 제한을 위한 여러 업무별/시스템별 계정이 존재
- 테이블: DBMS의 DB안에서 `데이터가 저장되는 형식`
- 스키마: 테이블이 `어떠한 구성`으로 되어있는지, 어떠한 정보를 가지고 있는지에 대한 `기본적인 구조 정의`

### 테이블
#### 테이블의 정의
- 엑셀에서의 워크시트와 같이 `행(로우)과 열(컬럼)을 갖는 2차원 구조`로 구성, 데이터를 입력하여 저장하는 최소 단위
- 컬럼은 `속성`이라고도 부른다.

#### 테이블의 특징
- 하나의 테이블은 `반드시 하나의 유저(계정) 소유`여야 한다.
- 테이블간 관계는 일대일, 일대다, 다대다 관계를 가질 수 있다.
- 테이블명은 `중복될 수 없지만`, 소유자가 `다른 경우 같은 이름으로 생성 가능`
- `행 단위`로 데이터가 입력, 삭제되며 `수정`은 `값 단위`로 가능

#### SQL
- 관계형 데이터베이스에서 데이터 조회, 조작, DBMS 시스템 관리 기능을 명령하는 언어
- 데이터 정의(DDL), 데이터 조작(DML), 데이터 제어 언어(DCL)등 으로 구분
- SQL 문법은 대소문자 구분하지 않는다.

#### 관계형 데이터베이스의 특징
- 데이터의 분류, 정렬, 탐색속도가 빠름
- 신뢰성이 높고 데이터의 무결성 보장
- 기존 작성된 스키마를 수정하기 어렵다
- 데이터베이스 부하를 분석하는 것이 어렵다

#### 데이터 무결성(intergrity)
- 데이터의 `정확성과 일관성`을 유지하고, `데이터에 결손`과 `부정합이 없음`을 보증하는 것
- 데이터베이스에 저장된 값과 그것을 표현하는 현실 비즈니스 모델의 값이 일치하는 정확성을 의미
- 데이터 무결성을 유지하는 것이 데이터베이스 관리시스템에 중요한 기능

#### 데이터 무결성 종류
- **개체 무결성**: `기본키`를 구성하는 컬럼은 `NULL이나 중복값을 가질 수 없다`.
- **참조 무결성**: `외래키` 값은 `null`이거나 `참조 테이블의 기본키` 값과 동일해야한다.
- **도메인 무결성**: 주어진 속성 값이 정의된 `도메인에 속한 값`이어야 한다.
- **NULL 무결성**: 특정 속성에 대해 `NULL을 허용하지 않는 특징`
- **고유 무결성**: 특정 속성에 대해 값이 `중복되지 않는 특징`
- **키 무결성**: 하나의 릴레이션에는 `적어도 하나의 키가 존재`한다.(테이블이 서로 관계를 가질 경우 반드시 하나 이상의 조인키를 가진다.)

<br>

* 도메인: 각 컬럼(속성)이 갖는 범위
* 릴레이션: 테이블 간 관계
* 튜플: 하나의 행
* 키: 식별자

#### ERD(Entity Relationship Diagram)
- 테이블 간 서로의 상관관계를 그림으로 표현한 것
- 구성요소: 엔티티(Entity), 관계(Relationship), 속성(Attribute)

<hr>

### SQL문
#### SQL의 종류
1. DDL(Data Definition Language)
- CREATE, ALTER, DROP, TRUNCATE
- 구조 자체를 변경

2. DML(Data Manipulation Language)
- INSERT, DELETE, UPDATE, MERGE
- 데이터를 변경

3. DCL(Data Control Language)
- GRANT, REVOKE

4. TCL(Transaction Control Language)
- COMMIT, ROLLBACK

5. DQL(Data Query Language)
- SELECT

#### SELECT 문 구조
~~~ SQL
SELECT * | 컬럼명 | 표현식
FROM 테이블명 또는 뷰명
WHERE 조회 조건
GROUP BY 그룹핑컬럼명
HAVING 그룹핑 필터링 조건
ORDER BY 정렬컬럼명
~~~

- `FROM -> WHERE -> GROUP BY -> HAVING -> SELECT -> ORDER BY` 순으로 실행

#### SELECT 절
- 불러올 컬럼명, 연산 결과를 작성하는 절
- `*`를 사용해 테이블 내 전체 컬럼명을 불러올 수 있다.
- 원하는 컬럼을 `,` 로 나열해 작성 가능
- 표현식: 원래 컬럼명을 제외한 모든 표현 가능한 대상(연산식, 기존 컬럼의 함수 변형식 포함)

#### 컬럼 Alias(별칭)
- 컬럼명 대신 출력할 `임시 이름` 지정(SELECT 절에서만 정의 가능, 원본 컬럼명은 변경되지 않음)
- 컬럼명 뒤 `AS` 와 함께 컬럼 별칭 전달, 생략가능

#### 특징 및 주의사항
- SELECT문보다 늦게 수행되는 `ORDER BY` 절에서만 컬럼 별칭 사용 가능
- 한글 사용 가능
- 이미 존재하는 예약어는 별칭으로 사용 불가
- 다음 경우 별칭에 반드시 쌍따옴표 전달이 필요
  - 별칭에 `공백`을 포함하는 경우
  - 별칭에 `특수문자`를 포함하는 경우
  - 별칭 그대로 전달할 경우(입력한 대소를 그대로 출력하고자 하는 경우)

#### FROM 절
- 데이터를 불러올 테이블명 또는 뷰명 전달
- 테이블 여러 개 전달 가능(`,`로 구분) -> 조인 없이 테이블명만 나열 시 카티시안 곱 발생 주의
- 테이블 별칭 선언 가능(오라클은 AS 사용 불가, SQL Server는 사용/생략가능)
- `오라클`에서는 `FROM절 생략 불가`(의미상 필요없는 경우 DUAL 테이블 선언)
- `SQL Server`에서는 FROM절 필요 없을 경우 `생략 가능`

<hr>

### 함수
#### 함수 정의
- input value가 있을 경우 그에 맞는 output value를 출력해주는 객체
- input value와 output value의 관계를 정의한 객체
- from절을 제외한 모든 절에서 사용 가능

#### 함수 기능
- 기본적인 쿼리문을 더욱 강력하게 해줌
- 데이터의 `계산을 수행`
- 개별 데이터의 항목 `수정`
- 표시할 날짜 및 숫자 형식 지정
- 열 데이터의 `유형 변환`

#### 함수의 종류(입력값의 수에 따라)
- 단일행 함수와 복수행 함수로 구분
- 단일행 함수: input과 output의 관계가 `1대1`
- 복수행 함수: 여러 건의 데이터를 동시에 입력받아 `하나의 요약값 리턴`(`그룹합수` or `집계함수`)

#### 입/출력값의 타입에 따른 함수 분류

1. `문자형 함수`
- 문자열 결합, 추출, 삭제 등 수행
- 단일행 함수 형태
- output은 대부분 문자값(LENGTH, INSTR 제외)

##### `문자함수 종류`

- **LOWER(대상)**: 문자열을 소문자로

- **UPPDER(대상)**: 문자열을 대문자로

- **SUBSTR(대상, m, n)**: 문자열 중 m위치에서 n개의 문자열 추출, n 생략 시 끝까지 추출, m은 음수를 가질 수 있는데 뒤에서 부터 탐색

- **INSTR(대상, 찾을문자열, m, n)**: 대상에서 찾을 문자열 위치 반환(m위치에서 시작, n번째 발견된 문자열 위치), m과n 생략 시 1로 해석, m은 음수를 가질 수 있는데 뒤에서부터 탐색하고 탐색방향도 역으로 탐색

- **LTRIM(대상, 삭제문자열)**: 문자열 중 특정 문자열을 왼쪽에서 삭제, 삭제문자열 생략 시 공백 삭제, 삭제문자열과 다른 문자열을 만나기 전까지 삭제

- **RTRIM(대상, 삭제문자열)**: 문자열 중 특정 문자열을 오른쪽에서 삭제, 삭제문자열 생략 시 공백 삭제, 삭제문자열과 다른 문자열을 만나기 전까지 삭제

- **TRIM(대상)**: 문자열 중 특정 문자열을 양쪽에서 삭제, 오라클에서는 공백만 삭제

- **LPAD(대상, n, 문자열)**: 대상 왼쪽에 문자열 추가하여 총 n의 길이 리턴

- **RPAD(대상, n, 문자열)**: 대상 오른쪽에 문자열 추가하여 총 n의 길이 리턴

- **CONCAT(대상1, 대상2)**: 문자열 결합, 두 개의 인수만 전달 가능

- **LENGTH(대상)**: 문자열 길이

- **REPLACE(대상, 찾을 문자열, 바꿀 문자열)**: 문자열 치환 및 삭제, 바꿀 문자열을 생략하거나 빈 문자열을 전달 시 찾을 문자열 삭제 가능

- **TRANSLATE(대상, 찾을 문자열, 바꿀 문자열)**: 글자를 1대1로 치환, 매칭되는 글자끼리 치환, 바꿀 문자열 생략 불가, 빈문자열 전달 시 null 리턴

2. `숫자형 함수` 
- 숫자를 입력하면 숫자 값을 반환
- 단일행 함수 형태
- 오라클과 SQL Server 거의 동일

##### `숫자함수 종류`
- **ABS(숫자)**: 절대값

- **ROUND(숫자, 자리수)**: 소수점 특정 자리에서 반올림, 자리수가 음수면 정수자리에서 반올림

- **TRUNC(숫자, 자리수)**: 소수점 특정 자리에서 버림

- **SIGN(숫자)**: 숫자가 양수면 1, 음수면 -1, 0이면 0 리턴

- **FLOOR(숫자)**: 작거나 같은 최대 정수 리턴

- **CEIL(숫자)**: 크거나 같은 최소 정수 리턴

- **MOD(숫자1, 숫자2)**: 숫자1을 숫자2로 나눈 나머지 반환

- **POWER(m,n)**: m의 n 거듭제곱

- **SQRT(숫자)**: 루트값 리턴

3. `날짜형 함수`
- 날짜 연산과 관련된 함수
- ORACLE과 SQL Server 함수 거의 다름

##### `날짜함수 종류`
- **SYSDATE**: 현재 날짜와 시간 리턴
- **CURRENT_DATE**: 현재 날짜 리턴
- **CURRENT_TIMESTAMP**: 현재 타임스탬프 리턴
- **ADD_MONTH(날짜, n)**: 날짜에서 n개월 후 날짜 리턴
- **MONTHS_BETWEEN(날짜1, 날짜2)**: 날짜1과 날짜2의 개월 수 리턴
- **LAST_DAY(날짜)**: 주어진 월의 마지막 날짜 리턴
- **NEXT_DAY(날짜, N)**: 주어진 날짜 이후 지정된 요일의 첫 번째 날짜 리턴
- **ROUND(날짜, 자리수)**: 날짜 반올림
- **TRUNC(날짜, 자리수)**: 날짜 버림

4. `변환 함수`
- 값의 `데이터 타입 변환`
- 문자를 숫자로, 숫자를 문자로, 날짜를 문자로

##### `변환함수 종류`
- **TO_NUMBER(문자)**: 숫자 타입으로 변경
- **TO_CHAR(대상, 포맷)**: 날짜 포맷으로 변경과 숫자의 포맷 변경 -> 결과는 문자타입
- **TO_DATE(문자, 포맷)**: 문자를 포맷 형식에 맞게 읽어 날짜로 리턴
- **FORMAT(날짜, 리턴)**: 날짜의 포맷 변경
- **CAST(대상 AS 데이터 타입)**: 대상을 주어진 타입으로 변환

5. `그룹 함수`
- 다중행 함수
- `여러 값`이 input값으로 들어가 `하나의 값`으로 리턴
- GROUP BY와 함께 사용

##### `그룹함수 종류`
- **COUNT(대상)**: 행의 수 리턴
- **SUM(대상)**: 총 합
- **AVG(대상)**: 평균
- **MIN(대상)**: 최솟값 리턴
- **MAX(대상)**: 최댓값 리턴
- **VARIANCE(대상)**: 분산 리턴
- **STDDEV(대상)**: 표준편차

6. `일반함수`
##### `일반함수 종류`
- **DECODE(대상, 값1, 리턴1, 값2, 리턴2, ... 그 외 리턴)**: 대상이 값1이면 리턴1, 값2이면 리턴2, ... 그외에는 그외 리턴값으로 리턴

- **NVL(대상, 최환값)**: 대상이 널일 때 치환값으로 치환하여 리턴

- **NVL2(대상, 치환값1, 치환값2)**: 대상이 널이 아니면 치환값1, 널이면 치환값2 리턴

- **COALESCE(대상1, 대상2, .... , 그 외 리턴)**: 대상들 중 널이 아닌 값 출력(가장 첫번째 부터), 모두 널일 경우 그외 리턴 값 리턴

- **ISNULL(대상, 치환값)**: 대상이 널이면 치환값 리턴

- **NULLIF(대상1, 대상2)**: 두 값이 같으면 널 리턴, 다르면 대상1 리턴

- **CASE문**: 조건별 치환 및 연산 수행

<hr>

### WHERE 절
#### WHERE절
- 테이블의 데이터 중 조건에 맞는 데이터만 조회하고자 할 때 사용
- 여러 조건 동시에 전달 가능(AND, OR)
- NULL 조회 시 `IS NULL, IS NOT NULL` 연산자 사용
- 조건 전달 시 타입 일치하는게 좋음
  - =: 같은 조건
  - !=, <>: 같지 않은 조건
  - &gt;: 큰 조건 검색
  - &gt;=: 크거나 같은 조건 검색
  - &lt;: 작은 조건
  - &lt;=: 작거나 같은 조건
  - BETWEEN a AND b: A와 B 사이 있는 범위 값 모두 검색
  - IN(a,b,c): A이거나 B이거나 C인 조건 검색
  - LIKE: 특정 패턴을 가지고 있는 조건 검색
  - IS NULL, IS NOT NULL: NULL값 검색, null이 아닌 값 검색
  - A AND B: A 조건과 B 조건을 모두 만족하는 값 검색
  - A OR B: A 조건이나 B 조건 중 한가지라도 만족하는 값 검색
  - NOT A: A가 아닌 모든 조건 검색

- 다른 절에서도 마찬가지로 문자나 날짜 상수 표현 시 `반드시 홑따옴표 사용`

#### LIKE 연산자
- 패턴 조건 전달 시 사용
- `%`, `_`와 함께 사용
- `%`: 자리수 제한 없는 모든
- `_`: _ 하나 당 한 자리수 의미

#### NOT 연산자
- 조건 결과의 반대, 즉, 여집합을 출력하는 연산
- NOT 뒤에 오는 연산 결과 반대 집합 출력
- 주로 NOT IN, NOT BETWEEN A AND B, NOT LIKE, NOT NULL

<hr>

### GROUP BY 절
#### GROUP BY절
- 각 행을 특정 조건에 따라 `그룹`으로 분리하여 계산하도록 하는 구문식
- GROUP BY절에 그룹을 지정할 컬럼을 전달
- 만약 그룹 연산에서 제외할 대상이 있다면 미리 WHERE절에서 해당 행 제외(WHERE이 먼저 수행)
- 그룹에 대한 조건은 `WHERE절에서 사용 불가`
- SELECT절에 집계 함수를 사용하여 그룹연산 결과 표현
- GROUP BY 절을 사용하게 되면 데이터가 요약되기 때문에 `요약 전 데이터와 함께 출력할 수 없다`.

#### HAVING 절
- `그룹함수 결과를 조건으로 사용할 때 사용`
- GROUP BY절 뒤에 작성
- 내부적 연산 순서가 SELECT보다 먼저이므로 SELECT에서 선언한 별칭 사용 불가

<Hr>

### ORDER BY
- 출력되는 `행 순서를 변경`하고자 할 때
- ORDER BY 뒤에 명시된 컬럼 순서대로 정렬 -> 1차 정렬, 2차 정렬 전달 가능
- 정렬 순서를 오름차순(ASC), 내림차순(DESC)로 전달(생략 시 오름차순 전달)
- 유일하게 SELECT절에 정의한 컬럼 `별칭 사용 가능`
- SELECT절에 선언한 순서대로 숫자 전달 가능(`컬럼명과 숫자 혼합 가능`)

#### 복합 정렬
- 먼저 정렬한 값의 동일한 결과가 있을경우 추가적으로 정렬 가능

#### NULL의 정렬
- 오라클은 기본적으로 NULL을 마지막에 배치
- NULLS LAST, NULLS FIRST를 명시하여 정렬 순서 변경 가능
- SQL Server는 처음에 배치

<hr>

### 조인
#### 조인
- 여러 테이블의 데이터를 사용하여 동시 출력하거나 참조할 경우 사용
- FROM 절에 조인할 테이블 나열
- 오라클은 테이블 나열 순서 중요하지 않지만, ANSI 표준은 OUTER JOIN 시 순서 중요
- WHERE절에서 조인 조건 작성
- 동일한 열 이름이 여러 테이블에 존재할 경우 열 이름 앞에 테이블 이름이나 별칭 붙임

#### 조인의 조건
1. 조건의 형태에 따라
- EQUI JOIN(등가 조인): JOIN 조건이 동등 조건인 경우
- NON EQUI JOIN(): JOIN 조건이 동등 조건이 아닌경우

2. 조인 결과에 따라
- INNER JOIN: JOIN `조건에 성립하는 데이터만 출력`
- OUTER JOIN: JOIN `조건에 성립하지 않는 데이터도 출력`, LEFT, RIGHT, FULL OUTER
- NATURAL JOIN: 조인조건 생략 시 두 테이블에 `같은 이름으로 자연 연결`되는 조인
- CROSS JOIN: 조인조건 생략 시 두 테이블의 `발생 가능한 모든 행 출력`
- SELF JOIN: 하나의 테이블을 `두 번 이상 참조`하여 연결하는 조인

#### EQUI JOIN(등가 조인)
- 조인 조건이 `=(equal)` 비교를 통해 같은 값을 가지는 행을 연결하여 결과를 얻는 조인 방법
- SQL 명령문에서 가장 많이 사용하는 조인 방법
- FROM절에 조인하고자 하는 테이블 모두 명시
- FROM절에 명시하는 테이블은 별칭 사용 가능
- WHERE절에 두 테이블의 공통 컬럼에 대한 조인 조건을 나열

#### NON-EQUI JOIN
- 테이블을 연결짓는 조인 컬럼에 대한 비교 조건이 `<`, `BETWEEN A AND B`와 같이 `=`이 아닌 연산자를 사용하는 경우의 조인조건

#### 세 테이블 이상의 조인
- 관계를 잘 파악하여 모든 테이블이 연결되도록 조인 조건 명시
- N개 테이블의 경우 최소 n-1개의 조인 조건 필요

#### SELF JOIN
- 한 테이블 내 각 행 끼리 관계를 갖는 경우 조인 기법
- 한 테이블을 참조할 때마다 명시
- 테이블명이 중복되므로 반드시 테이블 별칭 사용

<hr>

### 표준 조인
- ANSI 표준으로 작성되는 INNER JOIN, CROSS JOIN, NATURAL JOIN, OUTER JOIN

#### INNER JOIN
- 내부 조인이라고 하며, 조인 조건이 일치하는 행만 추출(오라클 기본 조인)
- ANSI 표준의 경우 FROM절에 `INNER JOIN`이나 `JOIN`을 명시
- ANSI 표준의 경우 `USING`이나 `ON` 조건절 필수적으로 사용

#### ON 절
- 조인할 양 컬럼의 컬럼명이 서로 다르더라도 사용 가능
- ON 조건의 괄호는 `옵션`(생략 가능)
- 컬럼명이 같을 경우 테이블의 이름이나 별칭을 사용하여 명확하게 지정
- ON 조건절에서 조인조건 명시, WHERE절에서는 일반조건 명시

#### USING 절
- 조인할 컬럼명이 같을 경우 사용
- `Alias나 테이블 이름 같은 접두사 붙이기 불가`
- `괄호 필수`

#### NATURAL JOIN
- 두 테이블 간의 `동일한 이름을 가진 모든 컬럼`들에 대해 EQUI JOIN 수행
- USING, ON, WHERE 절에서 `조건 정의 불가`
- JOIN에 사용된 컬럼들은 `데이터 유형이 동일`해야 하며 접두사를 사용불가

#### CROSS JOIN
- 테이블 간 JOIN 조건이 없는 경우 생성 가능한 모든 데이터들의 조합(카타시안 곱 출력)
- 양쪽 테이블 행의 수를 곱한 수의 데이터 조합 발생

#### OUTER JOIN
- JOIN 조건에서 `동일한 값이 없는 행도 반환`할 때 사용
- 두 테이블 중 한쪽에 null을 가지면 EQUI JOIN 시 출력되지 않는다.
- 테이블 기준에 따라 LEFT, RIGHT, FULL로 구분
- OUTER 생략 가능

##### OUTER JOIN의 종류
1. **LEFT (OUTER) JOIN**
- FROM절에 나열된 왼쪽 테이블에 해당하는 데이터를 읽은 뒤, 우측 테이블에서 JOIN 대상을 읽어옴
- `왼쪽 테이블이 기준`이 되어 오른쪽 테이블 데이터를 채우는 방식
- `우측 값에서 같은 값이 없는 경우 null`로 출력

2. **RIGHT (OUTER) JOIN**
- `오른쪽 테이블`기준으로 왼쪽 테이블 데이터를 채우는 방식

3. **FULL (OUTER) JOIN**
- 두 테이블 전체 기준으로 결과를 생성하여 중복 데이터는 삭제 후 리턴
- LEFT OUTER JOIN 결과와 RIGHT OUTER JOIN 결과의 UNION 연산 리턴과 동일


## SQL 활용
### 서브쿼리
- SQL문 안에 있는 또 다른 SELECT문
- ()로 감싸며 하나의 값, 행, 테이블 처럼 활용

#### 서브쿼리 유형
- 스칼라 서브쿼리: 결과가 단일값
- 인라인 뷰: 결과가 다중 행/열, 테이블처럼 사용
- 상관 서브쿼리: 외부 쿼리 값 참조
- EXISTS 서브쿼리: 존재 여부 판단

<hr>

### 집합 연산자
- 두 개 이상의 SELECT문 결과르 하나로 결합

#### 집합 연산자 종류
- UNION: 두 쿼리 결과 합침(중복 제거)
- UNION ALL: 두 쿼리 결과 합침(중복 제거X)
- INTERSECT: 두 쿼리의 공통 행
- MINUS, EXCEPT: 첫 쿼리 결과에서 두 번째 쿼리 결과 뺌

#### 집합 연산자의 사용 조건
1. SELECT절의 컬럼 수와 순서가 같아야함
2. 각 컬럼의 데이터 타입이 호환되어야 함
3. ORDER BY는 전체 쿼리 마지막에만 적용

<hr>

### 그룹 함수
- 여러 행의 값을 하나의 결과로 요약하는 함수

#### 주요 그룹 함수
- COUNT(*): 행의 개수
- COUNT(컬럼): NULL을 제외한 값의 개수
- SUM(컬럼): 숫자 컬럼의 합계
- AVG(): 숫자 컬럼 평균
- MAX(): 최대값
- MIN(): 최소값

COUNT(*)는 NULL 포함, COUNT(컬럼)은 NULL 제외, 나머지 그룹함수는 NULL 제외하고 계산

<hr>

### 윈도우 함수
- 행을 그룹으로 나누지 않고도 집계 함수를 각 행에 적용할 수 있는 함수
- GROUP BY는 그룹 단위로 묶어 행 수를 줄임
- 윈도우 함수는 전체 혹은 파티션을 기준으로 계산하지만 모든 행 유지

```sql
<윈도우 함수> OVER (
  [PARTITION BY <컬럼>]
  [ORDER BY <컬럼>]
  [ROWS BETWEEN ...]
)
```

#### 윈도우 함수 종류
- ROW_NUMBER(): 순번(중복 없이 순서 매김)
- RANK(): 동일한 값은 같은 순위, 다음 순위는 건너뜀
- DENSE_RANK(): 동일한 값에 같은 순위, 다음 순위 건너뛰지 않음
- SUM(), AVG(), COUNT(): 파티션 내 누적 계산
- LAG(), LEAD(): 이전/ 다음 행의 값 참조
- FIRST_VALUE(), LAST_VALUE(): 파티션 내 첫/마지막 값 가져오기

<hr>

### TOP N 쿼리
- 상위 N개 행만 조회하는 쿼리

#### TOP N 쿼리를 작성하는 방법
- ORDER BY + LIMIT
- 서브쿼리 + ROWNUM
- 윈도우 함수

<hr>

### 계층형 질의
- 자기 참조 구조를 가지는 데이터를 재귀적으로 조회할 때 사용

```SQL
SELECT (LEVEL,) ...
FROM 테이블명
START WITH 조건 -- 루트 노드
CONNECT BY PRIOR 자식컬럼 = 부모컬럼 -- 부모-자식 관계 정의
```

#### 셀프조인
- 하나의 테이블을 자기 자신과 조인하는 방식
```SQL
SELECT E.EMPNO AS 사번, E.ENAME AS 사원명,
       M.EMPNO AS 상사번호, M.ENAME AS 상사명
FROM EMP E
LEFT JOIN EMP M ON E.MGR = M.EMPNO;
```

ORDER SIBLINGS BY으로 형제 노드 정렬 가능

CONNECT_BY_ISLEAF = 1: 자식이 없는 노드면 1

<hr>

### PIVOT절
- 행 데이터를 특정 컬럼 기준으로 열로 바꾸는 기능

```sql
SELECT * FROM
(
  SELECT <행 기준 컬럼>, <열로 변환할 값 컬럼>, <집계할 값 컬럼>
  FROM 테이블명
)
PIVOT
(
  <집계 함수>(<집계할 값 컬럼>)
  FOR <열로 변환할 값 컬럼> IN (<변환할 값1>, <변환할 값2>, ...)
);

```

```sql
SELECT <행 기준 컬럼>, <새로 생성할 열 이름>, <새로 생성할 값 컬럼>
FROM 테이블명
UNPIVOT
(
  <새로 생성할 값 컬럼>
  FOR <새로 생성할 열 이름>
  IN (<변환할 열1>, <변환할 열2>, ...)
);
```