1. 문서 목적 (README 수준 안내)

이 Instruction.md 는 기존 애플리케이션 유지보수·확장 작업에서
•	AI 에이전트를 어디에, 어떻게 통합할지
•	TDD 프로세스로 기능을 검증·배포할지
구체적인 PRD(Product Requirements Document) 를 자동으로 생성하기 위한 “질문 ↔ 응답 형식 템플릿”이다.
AI Agent(예: ChatGPT, Claude, 사내 LLM 등)는 본 템플릿을 따라 질문을 던지고, 답변들을 조합해 최종 PRD를 작성한다.


2. PRD 최종 산출물 구조 (섹션 정의)
    1.	개요 / 배경
    2.	목표 및 성공 지표
    3.	현행 시스템 요약
    4.	변경·확장 범위 (Feature/Issue 리스트)
    5. 아키텍처 개요 & Mermaid 다이어그램
    6. 데이터베이스 스키마 변화
    7. API 계약서 변화
    8. 도메인 지식 / 업무 규칙
    9. 사용 기술 & 라이브러리
    10. TDD 전략
           • 사용자 스토리 → Acceptance Criteria → 세분 테스트 시나리오
           • Given-When-Then 표준
           • **Red-Green-Refactor 사이클 강제**
           – Red: 실패하는 단위 테스트 작성 → CI 파이프라인에서 레드 확인  
           – Green: 최소한의 코드로 테스트 통과  
           – Refactor: 리팩터링 후 동일 테스트 통과 보증
           • 테스트 실행/커버리지 리포트 자동 첨부 (JUnit XML, coverage ≥ N%)
    11. 비기능 요구 (성능, 보안, 감사, 회귀 테스트 범위)
    12. 릴리스 플랜 & 롤백 기준
    13. 부록 (용어 정의, 레퍼런스 링크 등)

⸻

3. AI Agent가 순차적으로 물어볼 “질문 세트”

각 단계는 “❓ 질문 → ⬆️ 답변 채우기” 패턴을 반복한다.
에이전트는 빠진 정보가 있을 때만 후속 질문을 한다.

3-1. 메타데이터 수집

❓ PRD 제목 / 버전?  
❓ 작성 날짜 & 작성자?

3-2. 현행 시스템 파악

❓ 현 애플리케이션의 한 줄 요약?  
❓ 배포 구조(모놀리식·마이크로서비스)?  
❓ 현재 주요 Pain Point 또는 유지보수 이슈는?

3-3. 기능 변경·추가 범위

❓ 이번 스프린트/릴리스에서 해결하려는 기능·버그 리스트? (번호/제목/설명)

3-4. 데이터베이스

❓ 신규/수정 테이블 ERD?  
❓ 인덱스, FK, 파티셔닝 고려사항?

3-5. API

❓ 추가·변경 엔드포인트(URI, Method, Req/Res JSON)?  
❓ 인증/인가 흐름?

3-6. 도메인 지식

❓ 업무 규칙·계산 식·용어(Glossary)?

3-7. 기술 스택

❓ 사용 언어/프레임워크 버전?  
❓ 주요 라이브러리(LLM SDK, 테스트 프레임워크 등)?

3-8. TDD 정책

❓ 테스트 대상 모듈/클래스?  
❓ 필수 시나리오(Given-When-Then 형태)와 엣지 케이스?  
❓ 목/스텁 전략?  
❓ 코드 커버리지 목표(%, 유형: Statement·Branch·Mutation)?  
❓ Red-Green-Refactor 주기를 팀이 어떻게 운영합니까?
   – 코드 리뷰 규칙, 브랜치 전략, 커밋 메시지 패턴(“test:”, “feat:”, “refactor:”) 등
❓ Refactor 단계에서 성능·가독성·보안 리뷰 체크리스트는?

⸻

4. PRD 자동 작성 규칙
    1.	모든 질문이 답변되면 AI Agent는 2-단계 출력을 수행한다.
    1.	마크다운 PRD 템플릿에 답변을 매핑
    2.	Mermaid 흐름도 2종 자동 삽입
          •	시스템 구성도(graph TD)
          •	주요 유스케이스 시퀀스(sequenceDiagram)
    2.	PRD는 하나의 .md 파일로 완결한다.
    3.	Git 커밋은 사용자가 별도로 지시할 때만 수행.
    4.	TDD 섹션 맨 끝에 tests/ 디렉터리 구조 & 대표 테스트 코드 블록 예시를 포함한다.
    5.	모든 표/코드 예시는 복사-붙여넣기 친화적 으로 80글자 이하 가로폭을 유지한다.
    6. TDD 섹션은 Red-Green-Refactor 흐름을 단계별 표로 명시해야 한다.

⸻

5. 예시 Mermaid 자동 생성 섹션

### 6. Architecture Diagram (auto-generated)

```mermaid
graph TD
    subgraph Client
        Browser -->|REST| API_GW
    end

    subgraph Backend
        API_GW --> ServiceA
        ServiceA -->|invoke| AIAgent
        AIAgent -->|vector search| VectorDB
        ServiceA --> DBMain[(RDBMS)]
    end

sequenceDiagram
    participant User
    participant UI
    participant API
    participant Agent
    participant DB

    User->>UI: 입력(기능 수정 요청)
    UI->>API: POST /issue
    API->>Agent: 분석 + LLM 호출
    Agent->>DB: 조회·업데이트
    Agent-->>API: 결과 & 테스트 시나리오
    API-->>UI: 응답(JSON)
```

⸻

6. 작성 시 체크리스트 (개발자용)
	•	모든 질문에 답변했는가
	•	Mermaid 그래프가 로컬 프리뷰에서 렌더링 되는가
	•	TDD 커버리지 목표가 현실적인가
	•	AI 에이전트 프롬프트에 개인정보가 포함되지 않는가
	•	배포 플랜에 롤백 절차가 포함됐는가
    •   Red-Green-Refactor 표가 포함됐는가
    •	Refactor 단계 후 커버리지·성능이 악화되지 않았는가