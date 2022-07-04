### Information

![image-20220624122247269](https://raw.githubusercontent.com/is3js/screenshots/main/image-20220624122247269.png)

- Director
    - Director는 외부로부터 Paper(기획서)를 받아온다. 
        - Paper에는 2가지 범주(Client, ServerClient)가 있다.
        - Director는 Paper들을 내부필드에 DB처럼 HashMap에 name으로 매핑해놓음.
    - Director는 기획서를 보고 Programmer(개발자)들을 섭외한다.
        - Programmer에는 2가지 범주(FrontEnd, BackEnd)가 있다.
        - Programmer는 Paper를 보고(-> 아는 관계) 이를 바탕으로 개발한다.
    - Director는 paper를 받아와, 그에 맞는 programmer를 섭외하고, programmer에게 paper을 던져줘서 프로그램을 개발하라고 시킨다.
        - Director는 2종류의 범주(Paper, Programmer)의 추상층을 바라본다. 

- Paper
    1. Client: 클라이언트 프로젝트만 만드는 기획서
    2. ServerClient: 서버와 클라이언트 2개 프로젝트를 만드는 기획서 

- Programmer
    1. FrontEnd
    2. BackEnd



### Description

- **코드 연습법**

	- clone후 `practice` branch에서 실습
	- `pracitce-sample` branch는 미리 정답을 구해놓은 branch

- **학습 내용**

	- 과정(Procedure)의 1~3번은 실습과정에 포함시키지 않았음. 이론만 읽어볼 것
	- LSP위반(부모형이 자식형을 대체못함)을 **Template Method Pattern**으로 해결
	- OCP위반(구상체 추가시마다 if가 늘어나는 문제)를 **Strategy Pattern**으로 해결
	- **구상체들을 모두 abstract class화** 하여 Main까지 개별로직(분기유발 구문)을 구현하는 훅메서드/전략메서드들을 Main까지 미룸



### Problems

- 각 Programmer 구상체들(프론트, 백)은 각자의 구상체 명세Paper(프-Client, 백-ServerClient)에 맞게 정보를 빼내와야한다.
- 각 Programmer 들은 파라미터에서 추상체 Paper를 받았지만
	- 전략패턴 -> 전략메서드()호출로 인해 자동으로 필요 로직이 실행되는 게 아니라
	- **추상체 -> 특정 구상체로 타입확인(if instanceof) -> 특정 구상체로 타입변환(다운캐스팅, OCP위반)해야하는 상황이다.**
		- 다운캐스팅 -> 확장을 못하게 된다
		- if를 유지 -> **goto를 일으켜 컴파일에러, 런타임에러를 발생시키지 않아, 문제해결이 힘들어진다.**



### Procedure

#### 이론(1~3)

- [base domain 파일 github](https://github.com/LenKIM/object-book/commit/629c078afffdbc76aba3ade318e0b1c004b4cf96#diff-951161e362c78e0593e03eb34335ee8cf6e0a9f3412638d338dd0d63d8f76c25)

1. **[연습시 미실습] LSP위반**

	- 문제점: **Programmer의 각 구상체(BackEnd, FrontEnd)내부에** `추상체(Paper)를 넘겨받지만, 정보가 부족하여 구상체 종류(ServerClient, Client)를 instanceof로 확인`(=LSP위반, 부모형이 자식형을 대신처리 못함)해서 처리해야하는 상황이다.
		- 이유: Paper가 추상체이지만, 마커인터페이스역할만 해서, 한번에 처리할 공통로직이 없는 추상체
		  ![image-20220618162318702](https://raw.githubusercontent.com/is2js/screenshots/main/image-20220618162318702.png)
		  ![image-20220618162348491](https://raw.githubusercontent.com/is2js/screenshots/main/image-20220618162348491.png)

	1. 서로 다른 필드를 가지는 `Server`, `ClientServer`의 추상체 `Paper`를 받더라도, 마커인터페이스로서 한번에 일시키는 메소드가 없고 카테고리만 만들어서
		- 이것을 받는 Programmer가 내부에서 **구상체 확인 by isinstanceof**을 해야한다.
		- 부모형인 Paper자체만으로 자식형 Server, ClientServer가 알아서 처리되지 못한다면, **LSP 위반**이다.
		- **구상체 추가시 if가 추가되는 OCP위반**에 선행되는 문제점이 **부모형으로 자식형을 대체 못함(LSP위반)**이다.

2. **[연습시 미실습]**LSP(isinstanceof) 해결 시행착오 by 헐리웃 원칙

	- 해결책: **헐리운원칙 = 시켜라 -> `특히 구상체마다 getter로 묻지말고, 추상체에 공통로직 추상메서드로 한번에 일하도록 메서드 제공해서 시켜라`**

		- 추상체에게 시키면 -> 추상메서드가 생기고 -> 개별 구현체들도 개별 구현해서, 스스로 일을 하게 됨. 추상체에게 시킨다 = 내부에서 구현체에게도 구현되어 구상체에게도 시키는 것이나 마찬가지다.

	- **간과한점: 구현체에게 물어보는 것을, 추상체로 시킬 경우, 그 기능(setter)가 구현체별로 생기는데, `과정에서 내부에 책임을 위임한 쪽(FrontEnd -> Programmer)도 추상체일 경우, 위임하기전에 상/하위 관계를 따져봐야한다`**

		- **시키기 전에, 추상체vs추상체의 책임위임이면, 하위(N)도메인에게 시킨 상태여야한다.**
		- 상위(1)도메인에게 시킬경우, 그 관계가 1:N이어서 N에까지 여파가 도달해버린다.
		- 지금의 경우, paper.setData(this=frontEnd)로 paper가 1쪽인 상위도메인인데 일을시키면
		- paper의 구현체마다 setData가 생성되나, 그 내부에서 frontend.set필드들 뿐만 아니라 backend.set필드들의 새로운 setter들이 N쪽(Programmer)구현체마다 생겨서, instanceof도 또 확인해야하는 문제가 생긴다.
		- **추상체vs추상체는 N쪽(하위)도메인에 `if instance 물어보는 책임`을 가진상태에서 차후 `generic`으로 추상체vs추상체의 case를 해결해야한다.**
		  ![image-20220618164322630](https://raw.githubusercontent.com/is2js/screenshots/main/image-20220618164322630.png)

	- **시행착오 겪어보기**:

		1. LSP위반(추상체로 처리 안됨)을 헐**리웃원칙으로 시킬 땐 확인부터해야한다**.

			1. 추상체에게 일을 시킬 때, 나도 추상체냐?
			2. 추상체vs추상체면 -> 상위(1)에서 하위(N)으로 시키는게 맞느냐?

			- 현재는 paper가 1이면서 상위라서.. paper에게 시킬 때, 나또한 추상체로 넘겨줘야하고, 그 개별구현체를 확인해야한다.

			3. 헐리웃원칙을 적용하기 전에 추상체vs추상체와 관계까지 확인한 뒤 적용시켜야한다.

		   ![cbcf8888-1ea9-49ba-8bea-075afac149f6](https://raw.githubusercontent.com/is2js/screenshots/main/cbcf8888-1ea9-49ba-8bea-075afac149f6.gif)

		2. 시행착오를 한번 겪어보기 위해 1쪽인 Paper추상체에게 알아서 하도록 시켜보자.

			1. 일단 `FrontEnd`내부에서 Paper-`Client`에게 시켜본다.

			- 시킬일을 메서드로 추출
			- Client로 메서드 이동
			- **FrontEnd(구현체)가 인자로 잡힘**
				- 넘길 때, this가 FrontEnd가 아닌  추상체Programmer로 넘어가야, BackEnd도 일을 시킬 수 있게 되니, backEnd처리시 처리해본다.

		   ![aa9ac4fe-4754-4df5-a3b9-e9af59bff280](https://raw.githubusercontent.com/is2js/screenshots/main/aa9ac4fe-4754-4df5-a3b9-e9af59bff280.gif)

			2. `Backend`내부에서 Paper-`ServerClient`에게 시켜본다.

			- Paper는 이제 추상체Programmer를 받아서 일을 하긴 하지만, 파라미터로 온 `Programmer도 역시 한번에 일시키는 메서드`가 없어서, **각 구현체(FrontEnd, BackEnd)마다 서로 다른 필드 -> 서로 다른 Setter을 가질 수 밖에 없다**

				1. 인터페이스에서 안쓰는 기능도 올려주거나

			  ![5c03bba3-7b9d-42e0-abc1-c95e7da7088b](https://raw.githubusercontent.com/is2js/screenshots/main/5c03bba3-7b9d-42e0-abc1-c95e7da7088b.gif)

				2. instanceof로 물어보고 개별setter만 사용하도록 처리할 수밖에 없다.

				- Programmer추상체를 파라미터로 받았는데, 개별setter를 사용해야하니, **물어보고 다운캐스팅 할 수 밖에 없는 구조**

			  ![17501407-ae45-48f2-9384-171a5738e4e7](https://raw.githubusercontent.com/is2js/screenshots/main/17501407-ae45-48f2-9384-171a5738e4e7.gif)

			3. paper(1)에게 일을 시키면서, Programer(N)을 건네주었지만, 1:N관계가 유지되는 한,

			   ![image-20220618172839846](https://raw.githubusercontent.com/is2js/screenshots/main/image-20220618172839846.png)

			   ![image-20220618172850641](https://raw.githubusercontent.com/is2js/screenshots/main/image-20220618172850641.png)

	- 시행착오 끝에

		1. **추상체vs추상체라면, N에게 물어보더라도 , 1에게는 시키지 않아야하는 구나**

		- **N(programmer)에서 1(Paper)를 받은 상태에서  `1:1`관계를 유지한체로 다른처리(`제네릭`)가 되어야하는구나.**

		2. **추상체vs추상체에서 instanceof를 제거하는 방법은 `제네릭`을 써야하는 구나**

3. **[연습시 미실습] Paper(1)에게 시키 전의 코드로 다시 롤백 및 해결책 생각(구상체에게 안물어보도록 추상체`Programmer`에 정보를 많게 하기 by `템플릿메서드패턴`)**

	- paper에 시키는 setData()이 없던, 원래 init code로 롤백

		- 1쪽인 Paper에 N의 Programmer가 넘어오면, 1(serverclient):N(frontend, backend)의 관계 -> `n개의 instanceof가 생겨 제네릭 적용이 불가`해진다

	- **Programmer에 `템플릿메서드 패턴` 적용**

		- Programmer는 N쪽이라 1을 받아서 일을 하는 쪽이지만, **구현체들이 public 공통로직이 존재하고, 개별구현을 싸고 있는 템플릿메소드 패턴이라면, 개별구현만 구현체들에게 `protected abstact step메서드(훅메서드) 구현`하게 하고, public 템플릿메소드는 구현안해도 물려받아 사용하게끔 중복코드를 제거**해야한다.

	- **추상층에 `1개의 템플리 메소드로 공통코드`를 올리려는 목적은`추상층이 포함하는 내용(공통로직)이 일반적으로 적어서` -> 추상층에 정보가 적다 -> `다운캐스팅해서 물어볼 수 밖에 없는게 일반적`이기 때문이다.**
	  **추상체는 인터페이스든(전략메서드?!) , 추상클래스든(템플릿메소드) `메서드는  1개이하`여야한다. 그럼에도 불구하고 추상층의 정보가 적지않으려면?? 템플릿메소드 패턴을 사용한다.**



#### 실습(4~6)


4. **[실습시작 `practice` branch ]** LSP위반과 별개로 공통로직을 가진 구현체들의 추상층을  마커인터페이스 -> 추상클래스(템플릿메서드패턴=`외부호출용 public 1개` + 그 내부에서 `step별 자식들이 개별구현할 proteced abstract 메서드들`)의 추상클래스로 만들기
	- **문제점: 공통로직을 가진 `BackEnd`, `FrontEnd`는 추상층 정보력을 높이기 위해 `마커인터페이스` Programmer 대신 -> `추상클래스` with 템플릿메소드 패턴의 추상층을 가져야한다.**
		- **공통로직을 추상층에 올리지 않으면 `DRY원칙 위반`이다.**
		- 추상클래스도 메서드가 1개이하이면서 `정보를 많이 담은 추상층`으로 만드는 방법이 `템플릿 메소드 패턴`을 이용하는 것이다.
	- **해결부터: `Compare Files`를 사용한 템플릿메서드 패턴으로 추상클래스 추출하기**
		- [Object)TemplateMethod for DRY(개발자의세계)](https://blog.chojaeseong.com/java/%EC%9A%B0%ED%85%8C%EC%BD%94/oop/object/%EA%B0%9C%EB%B0%9C%EC%9E%90%EC%9D%98%EC%84%B8%EA%B3%84/templatemethod/pattern/comparefiles/2022/06/18/Object_Template_method_pattern_1_dry.html)
	- **이미 템플릿메소드 패턴(`public 메서드 1개` + `개별 구현 로직은 내부에 메소드로 추출`하여 템플릿을 만들어놔야한다.**
		- **그래야 `@OVerride로 추상클래스를 템플릿메소드패턴에 맞춰 추출`할 수 있다.**
		- 내부에서 protected abstract step메서드들을 자식들이 개별구현한다.
		- **public 템플릿 메소드는 개별 자식들에게는 안보이지만 물려받는다.**


5. LSP(instanceof)의 진짜 해결책: 제네릭

	- **문제점: public 템플릿메소드의 파라미터로 추상체(Paper,1) to 추상체(Programmer, N)으로 넘어왔지만, 구상체 `FrontEnd`, `BackEnd`의 개별구현되는 곳인 protected abstract `훅메서드(step메서드)에서 instanceof가 사용되는 것을 제거`해보자.**
	  ![015ddc06-55ab-4bf5-8062-454be6194530](https://raw.githubusercontent.com/is2js/screenshots/main/015ddc06-55ab-4bf5-8062-454be6194530.gif)

	- **해결부터:**
		- [Object) Generic for remove instanceof+@](https://blog.chojaeseong.com/java/%EC%9A%B0%ED%85%8C%EC%BD%94/oop/object/%EA%B0%9C%EB%B0%9C%EC%9E%90%EC%9D%98%EC%84%B8%EA%B3%84/generic/instanceof/2022/06/19/Obejct_generic_for_remove_instanceof.html)

	- `추상형`(인터페이스, 추상클래스) -> `if(OCP)`를 해결한다.
		- 형을 통일시켜서 외부에서 그에 맞는 형을 주입하도록하고, 내부에서 if로 판단하지 않게 한다.
	- **`제네릭`: 1:1관계를 만든 상태로 추상체 to 추상체로 처리를 위임했을 때 생기는 `1개의 instanceof(LSP)`를 해결한다.**
		- 추상층(Paper)을 넘겨주어도 받는 추상층(Programmer)의 개별구현체(FrontEnd or BackEnd)마다  어느 구현체(Client, ServerClinet)인지 확인할 수 밖에 없을 때(LSP위반), 이것을 해결해준다.
		- 구체적으로는, `넘어오는 추상체(paper)가 정보가 적어`서 공통메서드를 안가지고 있다면 100% 확인해서 처리해야하고
		- `넘어오는 추상체(Paper)`의 정보 양(공통기능 제공)과 무관하게, **받는 쪽(Programmer)에서 개별구현체마다 받아들이는 구현체가 달라야할 경우**(Front는 구현체1Client에만 있는 library를 / Back-server는 구현체2ServerClient에만 있는 server정보를 받아야함)해야하는 상황이라면, **구상체끼리 짝지어주어야한다. by 제네릭**
			- Programmer(N) <- Paper(1)
				- BackEnd(N구현체1) <- Serverclient (paper 1구현체1)
				- FrontEnd(N구현체2) <- Client (paper 1구현체2)
	- if(runtime에러), instanceof(context에러)를 미리 못 잡는데, 제네릭을 이용해서 instanceof를 제거하면, **제네릭은 type에러->  `type(형)`에 의해 에러를 내므로 -> `compile에러`로서 미리잡을 수 있게 된다.**
	- **`추상층에서 upperbound(타추상층) T형을 도입 -> 메서드의 인자로 사용`하면 -> 구상층에서는 upperbound의 구현체(타구현체)를 구상층(구현체들)에서 `<T>좁혀서 특정형 제네릭 + 메서드인자`로 사용할 수 있다.**
		- **제네릭을 사용하면,  추상층1에서 T extends 추상체2의 T 자리에 구상체2을 지원 -> 구상층1에서 형으로 (특정 구상체를 사용할 수 있게끔) 확 줄임**
	- **제네릭은 기계적으로 사용**할 수 있다. 매핑하는 것처럼
		- 현재 클래스의 추상층에 제네릭으로서 upperbound로 T형을 꽂아주고, 추상메소드 정의시 T를 사용해서 정의
		- 이 추상층을 -> 구현이나, 메소드정의시 T자리에 구상형을 직접 입력해서 정의

6.  OCP 위반의 해결책 -> 추상화 with 전략패턴

- **문제점**: `Director`의 runProject 속 `Paper`의 구상체를 확인하는**instanceof는 2개라서 제네릭으로 제거못한다 -> if 2개이상으로서 전략턴으로 제거한다.**

- **해결책**:
	- [Object) Strategy pattern for OCP](https://blog.chojaeseong.com/java/%EC%9A%B0%ED%85%8C%EC%BD%94/oop/object/%EA%B0%9C%EB%B0%9C%EC%9E%90%EC%9D%98%EC%84%B8%EA%B3%84/strategy/ocp/2022/06/20/Object_stratge_pattern_for_OCP.html)
