# AOP 실습 - 관점 지향 프로그래밍 마스터

**Spring AOP를 통한 관점 지향 프로그래밍(Aspect-Oriented Programming) 완전 학습 프로젝트**입니다. 인증/인가, 로깅, 성능 모니터링 등 실무에서 자주 사용되는 **횡단 관심사(Cross-Cutting Concern)** 처리를 실제 구현하며 학습합니다.

---

## 🎯 프로젝트 목표

| 목표 | 설명 |
|------|------|
| **AOP 개념 이해** | 관점 지향 프로그래밍의 핵심 개념 학습 |
| **포인트컷 및 어드바이스** | 횡단 관심사 분리 및 적용 방법 |
| **실무 패턴 학습** | 인증, 로깅, 성능 모니터링 구현 |
| **Aspect 작성** | @Aspect 어노테이션 활용 |
| **커스텀 어노테이션** | 메타 프로그래밍을 통한 AOP 활용 |

---

## 🛠 기술 스택

| 분야 | 기술 |
|------|------|
| **프레임워크** | Spring Boot 3.5.3 |
| **AOP** | Spring AOP (AspectJ) |
| **Java 버전** | 17 LTS |
| **빌드 도구** | Gradle |
| **테스트** | JUnit 5 |
| **패턴** | 데코레이터, 인터셉터, 프록시 |

---

## 📦 프로젝트 구조

```
aop-practice/                               # 루트 멀티모듈 프로젝트
│
├── settings.gradle                         # 모듈 설정
│
├── aop-board/                              # 게시판 AOP 적용
│   │
│   ├── build.gradle                        # aop-board 빌드 설정
│   │
│   └── src/
│       ├── main/
│       │   ├── java/com/laze/aopboard/
│       │   │   │
│       │   │   ├── AopBoardApplication.java # 진입점
│       │   │   │
│       │   │   ├── aop/                    # AOP 관점 정의
│       │   │   │   ├── AuthAspect.java    # 인증 검증
│       │   │   │   ├── LoggingAspect.java # 메서드 호출 로깅
│       │   │   │   │
│       │   │   │   └── annotation/         # 커스텀 어노테이션
│       │   │   │       ├── RequireAuth.java
│       │   │   │       ├── LogExecution.java
│       │   │   │       └── TrackingId.java
│       │   │   │
│       │   │   ├── controller/             # HTTP 요청 처리
│       │   │   │   ├── BoardController.java
│       │   │   │   └── UserController.java
│       │   │   │
│       │   │   ├── service/                # 비즈니스 로직
│       │   │   │   ├── BoardService.java
│       │   │   │   ├── PostService.java
│       │   │   │   └── UserService.java
│       │   │   │
│       │   │   ├── repository/             # 데이터 접근
│       │   │   │   ├── BoardRepository.java
│       │   │   │   ├── PostRepository.java
│       │   │   │   └── UserRepository.java
│       │   │   │
│       │   │   ├── dto/                    # 데이터 전송 객체
│       │   │   │   ├── BoardDto.java
│       │   │   │   ├── PostDto.java
│       │   │   │   └── UserDto.java
│       │   │   │
│       │   │   └── exception/              # 예외 처리
│       │   │       ├── AuthException.java
│       │   │       ├── BoardNotFoundException.java
│       │   │       └── GlobalExceptionHandler.java
│       │   │
│       │   └── resources/
│       │       ├── application.yml         # 설정
│       │       └── logback-spring.xml      # 로깅 설정
│       │
│       └── test/
│           └── java/com/laze/aopboard/     # 테스트 클래스
│               ├── AopBoardApplicationTests.java
│               ├── AuthAspectTest.java
│               └── ServiceTest.java
│
└── aop-performance-logger/                 # 성능 모니터링
    │
    ├── build.gradle                        # aop-performance-logger 빌드 설정
    │
    └── src/
        ├── main/
        │   ├── java/com/laze/aopperformancelogger/
        │   │   │
        │   │   ├── AopPerformanceLoggerApplication.java # 진입점
        │   │   │
        │   │   ├── PerformanceLoggerAspect.java         # 성능 측정
        │   │   │
        │   │   └── MySimpleService.java                 # 샘플 서비스
        │   │
        │   └── resources/
        │       └── application.yml         # 설정
        │
        └── test/
            └── java/com/laze/aopperformancelogger/
                └── PerformanceLoggerTest.java
```

---

## 🚀 빠른 시작

### 필수 요구사항

```bash
# Java 17+ 확인
java --version

# Gradle 확인
gradle --version
```

### 프로젝트 설정 및 실행

**1단계: 클론 및 빌드**
```bash
git clone https://github.com/L-a-z-e/aop-practice.git
cd aop-practice
gradle build
```

**2단계: 모듈별 실행**
```bash
# aop-board 모듈 실행 (포트 8080)
gradle :aop-board:bootRun

# 다른 터미널에서 aop-performance-logger 실행 (포트 8081)
gradle :aop-performance-logger:bootRun
```

**3단계: 접속 및 테스트**
```bash
# API 테스트
curl http://localhost:8080/api/boards
curl http://localhost:8081/api/performance
```

---

## 💡 핵심 개념

### 1. AOP의 개념

**AOP (Aspect-Oriented Programming)**는 관심사의 분리(Separation of Concerns)를 구현하는 프로그래밍 방식입니다.

```
기존 방식 (❌)
┌─────────────────────────────────────┐
│ 인증 검증                             │
├─────────────────────────────────────┤
│ 비즈니스 로직                         │
├─────────────────────────────────────┤
│ 로깅                                 │
├─────────────────────────────────────┤
│ 예외 처리                            │
└─────────────────────────────────────┘

AOP 방식 (✅)
┌──────────────────────────┐
│    비즈니스 로직          │ ← 순수한 비즈니스 로직만 집중
└──────────────────────────┘
        ↑    ↑    ↑
    인증  로깅  성능 (횡단 관심사)
```

### 2. AOP 용어

| 용어 | 설명 |
|------|------|
| **Aspect** | 횡단 관심사를 구현한 클래스 (@Aspect) |
| **Joinpoint** | AOP가 적용될 수 있는 지점 (메서드 호출 등) |
| **Pointcut** | Joinpoint의 부분집합 (@Pointcut) |
| **Advice** | Joinpoint에서 실행할 코드 (@Before, @After 등) |
| **Weaving** | Advice를 Joinpoint에 적용하는 과정 |

### 3. Advice 타입

```java
@Aspect
@Component
public class MyAspect {
    
    // 메서드 실행 전
    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        System.out.println("Before: " + joinPoint.getSignature());
    }
    
    // 메서드 정상 실행 후
    @AfterReturning(value = "pointcut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("After Returning: " + result);
    }
    
    // 메서드 예외 발생 후
    @AfterThrowing(value = "pointcut()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Exception exception) {
        System.out.println("After Throwing: " + exception.getMessage());
    }
    
    // 메서드 실행 후 (항상 실행)
    @After("pointcut()")
    public void after(JoinPoint joinPoint) {
        System.out.println("After: " + joinPoint.getSignature());
    }
    
    // 메서드 실행 전후 (가장 강력함)
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("Around Before: " + pjp.getSignature());
        try {
            Object result = pjp.proceed(); // 실제 메서드 실행
            System.out.println("Around After: " + result);
            return result;
        } catch (Throwable ex) {
            System.out.println("Around Exception: " + ex.getMessage());
            throw ex;
        }
    }
}
```

---

## 🏗 아키텍처

### AOP 적용 흐름

```
클라이언트 요청
    ↓
Spring 프록시 객체 생성
    ↓
@Before Advice 실행 (사전 처리)
    ↓
↓───────────────────────┐
│ 실제 비즈니스 로직 실행 │
└───────────────────────┘
    ↓
@AfterReturning Advice 또는 @AfterThrowing Advice
    ↓
@After Advice 실행 (사후 처리)
    ↓
응답 반환
```

---

## 📝 실전 예제 코드

### 1. 기본 Aspect 작성

**LoggingAspect.java**
```java
@Aspect
@Component
@Slf4j
public class LoggingAspect {
    
    // Pointcut 정의
    @Pointcut("execution(* com.laze.aopboard.service.*Service.*(..))")
    public void servicePointcut() {
    }
    
    // Before Advice
    @Before("servicePointcut()")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        
        log.info("=== 메서드 호출 시작 ===");
        log.info("메서드: {}", methodName);
        log.info("인자: {}", Arrays.toString(args));
    }
    
    // AfterReturning Advice
    @AfterReturning(value = "servicePointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        
        log.info("=== 메서드 호출 완료 ===");
        log.info("메서드: {}", methodName);
        log.info("반환값: {}", result);
    }
    
    // AfterThrowing Advice
    @AfterThrowing(value = "servicePointcut()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        String methodName = joinPoint.getSignature().getName();
        
        log.error("=== 메서드 호출 예외 ===");
        log.error("메서드: {}", methodName);
        log.error("예외: {}", exception.getMessage());
    }
}
```

### 2. 커스텀 어노테이션 활용

**RequireAuth.java**
```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireAuth {
    String role() default "USER";
}
```

**AuthAspect.java**
```java
@Aspect
@Component
@Slf4j
public class AuthAspect {
    
    // 커스텀 어노테이션을 기반으로 Pointcut 정의
    @Pointcut("@annotation(requireAuth)")
    public void authPointcut(RequireAuth requireAuth) {
    }
    
    @Before("authPointcut(requireAuth)")
    public void checkAuth(JoinPoint joinPoint, RequireAuth requireAuth) {
        String requiredRole = requireAuth.role();
        String currentUser = getCurrentUser();
        
        log.info("인증 확인: 사용자={}, 필요한 권한={}", currentUser, requiredRole);
        
        if (!hasRole(currentUser, requiredRole)) {
            throw new AuthException("권한 없음");
        }
    }
    
    private String getCurrentUser() {
        // 실제로는 SecurityContext에서 가져옴
        return "user123";
    }
    
    private boolean hasRole(String user, String role) {
        // 실제로는 DB에서 확인
        return true;
    }
}
```

**BoardController.java**
```java
@RestController
@RequestMapping("/api/boards")
public class BoardController {
    
    private final BoardService boardService;
    
    @PostMapping
    @RequireAuth(role = "ADMIN")
    public ResponseEntity<BoardDto> createBoard(@RequestBody BoardDto dto) {
        return ResponseEntity.ok(boardService.create(dto));
    }
    
    @DeleteMapping("/{id}")
    @RequireAuth(role = "ADMIN")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        boardService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

### 3. @Around를 이용한 성능 모니터링

**PerformanceLoggerAspect.java**
```java
@Aspect
@Component
@Slf4j
public class PerformanceLoggerAspect {
    
    @Pointcut("execution(* com.laze.aopperformancelogger..*.*(..))")
    public void allMethods() {
    }
    
    @Around("allMethods()")
    public Object measurePerformance(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = pjp.getSignature().getName();
        
        try {
            // 실제 메서드 실행
            Object result = pjp.proceed();
            
            return result;
        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            // 성능 로깅
            log.info("메서드: {}, 실행시간: {}ms", methodName, executionTime);
            
            // 임계값 초과 시 경고
            if (executionTime > 1000) {
                log.warn("⚠️ 메서드 {}는 {}ms로 느림 (임계값: 1000ms)", 
                         methodName, executionTime);
            }
        }
    }
}
```

**MySimpleService.java**
```java
@Service
@Slf4j
public class MySimpleService {
    
    public void fastMethod() {
        log.info("빠른 메서드 실행");
    }
    
    public void slowMethod() throws InterruptedException {
        log.info("느린 메서드 시작");
        Thread.sleep(1500); // 1.5초 지연
        log.info("느린 메서드 종료");
    }
}
```

### 4. 트래킹 ID를 통한 요청 추적

**TrackingIdAspect.java**
```java
@Aspect
@Component
@Slf4j
public class TrackingIdAspect {
    
    private static final ThreadLocal<String> trackingId = new ThreadLocal<>();
    
    @Pointcut("execution(* com.laze.aopboard.controller..*(..))")
    public void controllerPointcut() {
    }
    
    @Before("controllerPointcut()")
    public void generateTrackingId(JoinPoint joinPoint) {
        String id = UUID.randomUUID().toString();
        trackingId.set(id);
        
        MDC.put("trackingId", id);
        log.info("[TrackingId: {}] Controller 호출 - {}", id, joinPoint.getSignature());
    }
    
    @After("controllerPointcut()")
    public void clearTrackingId() {
        String id = trackingId.get();
        log.info("[TrackingId: {}] Controller 종료", id);
        
        trackingId.remove();
        MDC.remove("trackingId");
    }
    
    public static String getTrackingId() {
        return trackingId.get();
    }
}
```

---

## 🔍 Pointcut 표현식

### 기본 문법

```java
// 메서드 실행
@Pointcut("execution(* com.laze.aopboard.service.*Service.*(..))")

// 어노테이션
@Pointcut("@annotation(com.laze.aopboard.aop.annotation.RequireAuth)")

// 타입 레벨 어노테이션
@Pointcut("@within(org.springframework.stereotype.Service)")

// 메서드 인자 조건
@Pointcut("args(String, int)")

// 빈 이름
@Pointcut("bean(boardService)")

// 조합 (&&, ||, !)
@Pointcut("execution(* *..*Service.*(..)) && @annotation(RequireAuth)")
```

### 예제 Pointcut

```java
// 1. Service 패키지의 모든 메서드
@Pointcut("execution(* com.laze.aopboard.service.*.*(..))")

// 2. public 메서드만
@Pointcut("execution(public * com.laze.aopboard.service.*.*(..))")

// 3. save/update/delete로 시작하는 메서드
@Pointcut("execution(* com.laze.aopboard.service.*.save*(..))")

// 4. 첫 번째 인자가 Long인 메서드
@Pointcut("execution(* com.laze.aopboard.service.*.*(Long, ..))")

// 5. 여러 조건 조합
@Pointcut("(execution(* com.laze.aopboard.service.*.*(..))" +
          " && @annotation(RequireAuth))")
```

---

## 📊 AOP 적용 시나리오

### 시나리오 1: 인증 검증

```
요청 → AuthAspect 검증 → 권한 있음 → 비즈니스 로직 → 응답
                            ↓
                      권한 없음 → 401 Unauthorized
```

### 시나리오 2: 성능 모니터링

```
요청 → PerformanceAspect (시작시간 기록)
        → 비즈니스 로직 실행
        → PerformanceAspect (종료시간 기록, 실행시간 계산, 로그)
        → 응답
```

### 시나리오 3: 로깅 및 추적

```
요청 → TrackingIdAspect (고유 ID 생성)
        → LoggingAspect (메서드 입력 로깅)
        → 비즈니스 로직 실행
        → LoggingAspect (메서드 출력 로깅)
        → TrackingIdAspect (정리)
        → 응답
```

---

## 🎓 학습 체크리스트

### 기본 개념 (1주)
- [ ] AOP의 개념 및 필요성 이해
- [ ] Aspect, Pointcut, Advice 이해
- [ ] Advice 타입별 동작 방식 학습
- [ ] 기본 @Aspect 작성

### 포인트컷 (1주)
- [ ] 포인트컷 표현식 문법 학습
- [ ] 정규표현식 기반 Pointcut
- [ ] 어노테이션 기반 Pointcut
- [ ] 조건 조합 (@annotation && execution)

### 실무 패턴 (2주)
- [ ] 인증/인가 구현
- [ ] 로깅 및 추적 ID 구현
- [ ] 성능 모니터링 구현
- [ ] 캐싱 AOP 구현

### 고급 주제 (1주)
- [ ] AspectJ와 Spring AOP 차이
- [ ] 프록시 vs Weaving
- [ ] AOP 성능 튜닝
- [ ] 테스트 작성

---

## 🔧 설정 파일

### application.yml

```yaml
spring:
  application:
    name: aop-practice
  
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false

# AOP 설정
logging:
  level:
    root: INFO
    com.laze.aopboard: DEBUG
  pattern:
    console: "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"

# 액추에이터
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
```

---

## 🧪 테스트

### 기본 AOP 테스트

```java
@SpringBootTest
public class AuthAspectTest {
    
    @Autowired
    private BoardService boardService;
    
    @Test
    public void testAuthAspectWithValidUser() {
        // 권한이 있는 사용자로 테스트
        BoardDto board = new BoardDto("테스트", "내용");
        
        // 성공해야 함
        assertDoesNotThrow(() -> boardService.createBoard(board));
    }
    
    @Test
    public void testAuthAspectWithoutAuth() {
        // 권한이 없는 사용자로 테스트
        BoardDto board = new BoardDto("테스트", "내용");
        
        // AuthException 발생해야 함
        assertThrows(AuthException.class, 
                     () -> boardService.deleteBoard(1L));
    }
}
```

### 성능 로깅 테스트

```java
@SpringBootTest
public class PerformanceLoggerTest {
    
    @Autowired
    private MySimpleService mySimpleService;
    
    @Test
    public void testFastMethodPerformance() {
        long startTime = System.currentTimeMillis();
        mySimpleService.fastMethod();
        long duration = System.currentTimeMillis() - startTime;
        
        // 1초 이내여야 함
        assertTrue(duration < 1000);
    }
    
    @Test
    public void testSlowMethodPerformance() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        mySimpleService.slowMethod();
        long duration = System.currentTimeMillis() - startTime;
        
        // 1.5초 이상 걸려야 함
        assertTrue(duration >= 1500);
    }
}
```

---

## 📈 성능 영향도

| 항목 | 영향도 |
|------|--------|
| **메모리** | 프록시 객체 생성으로 약간의 추가 메모리 사용 |
| **응답시간** | 어드바이스 실행 시간에 따라 변동 (보통 1-5ms) |
| **CPU** | Pointcut 매칭 비용 (Spring이 최적화) |

### 성능 최적화 팁

1. **Pointcut 최적화** - 너무 광범위한 Pointcut 피하기
2. **어드바이스 최소화** - 필요한 것만 처리
3. **@Around 주의** - 가장 비용이 많이 드는 Advice
4. **조건 최우선** - && 연산자로 먼저 필터링

---

## 🔗 API 엔드포인트

### 게시판 API (aop-board)

| 메서드 | 엔드포인트 | 인증 | 설명 |
|--------|-----------|------|------|
| **GET** | `/api/boards` | 없음 | 게시판 목록 |
| **GET** | `/api/boards/{id}` | 없음 | 게시판 상세 |
| **POST** | `/api/boards` | ADMIN | 게시판 생성 |
| **PUT** | `/api/boards/{id}` | ADMIN | 게시판 수정 |
| **DELETE** | `/api/boards/{id}` | ADMIN | 게시판 삭제 |

### 성능 API (aop-performance-logger)

| 메서드 | 엔드포인트 | 설명 |
|--------|-----------|------|
| **GET** | `/api/performance/fast` | 빠른 메서드 호출 |
| **GET** | `/api/performance/slow` | 느린 메서드 호출 |

---

## 🐛 일반적인 문제 해결

### 1. AOP가 작동하지 않음

**원인**: 프록시 적용 실패 또는 Pointcut 불일치

```java
// ❌ 문제
@Component
public class MyService {
    public void method() { }
}

// ✅ 해결
@Service  // 또는 @Component
public class MyService {
    public void method() { }
}
```

### 2. Self-Invocation 문제

```java
// ❌ 문제 - AOP 미적용
@Service
public class MyService {
    public void methodA() {
        methodB(); // 프록시를 거치지 않음
    }
    
    @Transactional
    public void methodB() { }
}

// ✅ 해결
@Service
public class MyService {
    @Autowired
    private MyService myService;
    
    public void methodA() {
        myService.methodB(); // 프록시를 거쳐서 호출
    }
}
```

### 3. Pointcut 매칭 실패

```java
// ❌ 문제
@Pointcut("execution(* service.*.*(..))")  // 패키지 경로 불일치

// ✅ 해결
@Pointcut("execution(* com.laze.aopboard.service.*.*(..))")
```

---

## 📚 추가 학습 자료

### Spring AOP 심화 주제
1. **AspectJ** - Spring AOP보다 강력한 AOP 프레임워크
2. **Load-Time Weaving (LTW)** - 컴파일 시점 vs 런타임 위빙
3. **Runtime Weaving** - 런타임에 Aspect 동적 적용
4. **AOP와 트랜잭션** - @Transactional과 AOP의 관계

### 실무 활용
- 분산 트레이싱 (Distributed Tracing)
- API 버전 관리
- 속도 제한 (Rate Limiting)
- 캐싱 전략
- 동시성 제어

---

## 🎊 프로젝트 특징

✅ **멀티모듈 구조**
- aop-board: 게시판 시스템 + AOP
- aop-performance-logger: 성능 모니터링

✅ **실무 패턴**
- 인증/인가
- 로깅
- 성능 모니터링
- 요청 추적

✅ **학습 중심**
- 단순하고 이해하기 쉬운 코드
- 주석이 풍부한 예제
- 다양한 Advice 타입 활용

---

## 🤝 기여하기

이 프로젝트에 기여하고 싶다면:
1. Fork 후 feature 브랜치 생성
2. 변경사항 커밋
3. Pull Request 제출

---

## 📝 의존성

```gradle
// Spring Boot
implementation 'org.springframework.boot:spring-boot-starter-web'
implementation 'org.springframework.boot:spring-boot-starter-aop'

// Lombok
compileOnly 'org.projectlombok:lombok'
annotationProcessor 'org.projectlombok:lombok'

// 테스트
testImplementation 'org.springframework.boot:spring-boot-starter-test'
```
