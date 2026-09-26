# 🦁 AI-Powered Intelligent Zoo Care & Feeding Planner

A complete, full-stack decision support system and AI planner for zookeepers built with **Java 21**, **Spring Boot 3**, **SQLite**, and **Thymeleaf**.

---

## 📌 Project Overview
The **AI-Powered Intelligent Zoo Care & Feeding Planner** is designed as an autonomous decision system for zookeepers. It eliminates manual errors, optimizes food stock usage, schedules zookeeper shifts without overlaps, detects dietary shortages, generates dynamic enrichment activities, and performs real $A^*$ dynamic re-planning whenever food inventory or keeper availability changes.

All 14 AI concepts are implemented as **real, executable Java algorithms** in `com.example.zoo.ai` and `com.example.zoo.planner`.

---

## 🚀 Technology Stack
- **Language**: Java 21
- **Framework**: Spring Boot 3.2.4 (Spring Data JPA, Hibernate 6)
- **Database**: SQLite persistent database (`zoo.db`)
- **Frontend**: Thymeleaf, HTML5, CSS3 (Tailwind CSS ACME Enterprise Theme), Vanilla JS
- **Build Tool**: Maven

---

## 🤖 14 Mandatory AI Concepts & Executable Implementation Architecture

| AI Concept | Executable Java Class | Description & Algorithm |
|---|---|---|
| **A. Knowledge Representation** | `KnowledgeRepresentationService` | Taxonomy, diets, and species rules stored in SQLite `species_knowledge` & `species_foods`. |
| **B. Knowledge-Based Reasoning** | `KnowledgeReasoningService` | Infers candidate diets & age-adjusted portions for species. |
| **C. Rule-Based Reasoning** | `RuleEngineService` | Executable IF-THEN rules for suitability, capacity, and overlap constraints. |
| **D. Constraint Satisfaction (CSP)** | `FeedingCSPService` | Models variables $(A_i, T_j)$, domains (Foods, Keepers), and hard constraints. |
| **E. Constraint Propagation** | `ConstraintPropagationService` | Forward checking arc consistency to eliminate 0-stock foods/unavailable keepers. |
| **F. Backtracking Search** | `BacktrackingSolver` | Pure recursive depth-first backtracking search exploring CSP variable assignments. |
| **G. Utility-Based Decision** | `UtilityService` | Multi-objective scoring: $Utility = \text{Suitability} + \text{Preference} + \text{StockEfficiency} - \text{DisruptionCost}$. |
| **H. A\* Search** | `AStarReplanningService` | Graph search using `PriorityQueue` ($f(n) = g(n) + h(n)$) for dynamic re-planning. |
| **I. Planning** | `FeedingPlanningService` | Complete cognitive planner pipeline orchestrator. |
| **J. Probabilistic Reasoning** | `ObservationReasoningService` | Bayes-weighted scoring on food/water/activity logs flagging health anomaly status. |
| **K. Decision Making Under Uncertainty**| `DecisionService` | Selects optimal feasible stock alternatives when primary food is depleted. |
| **L. Intelligent Agent** | `ZooPlanningAgent` | Agent loop: `PERCEIVE` $\rightarrow$ `REASON` $\rightarrow$ `PLAN` $\rightarrow$ `DECIDE` $\rightarrow$ `ACT` $\rightarrow$ `MONITOR` $\rightarrow$ `RE-PLAN`. |
| **M. GPT / Prompt Engineering** | `ZooAssistantService` | Natural Language interface grounded strictly in SQLite tables. |
| **N. Explainable AI (XAI)** | `DecisionExplanationService` | Transparent step-by-step rationales for feeding choices and $A^*$ replan steps. |

---

## 🏛️ Project Directory Structure

```
ai-zoo-planner/
├── zoo.db                             # Persistent SQLite Database File
├── pom.xml                            # Maven Configuration
├── src/main/java/com/example/zoo/
│   ├── ZooPlannerApplication.java    # Main Entry Point
│   ├── ai/                            # 11 Real Executable Java AI Services
│   │   ├── KnowledgeRepresentationService.java
│   │   ├── KnowledgeReasoningService.java
│   │   ├── RuleEngineService.java
│   │   ├── ConstraintPropagationService.java
│   │   ├── UtilityService.java
│   │   ├── AStarReplanningService.java
│   │   ├── ObservationReasoningService.java
│   │   ├── DecisionService.java
│   │   ├── ZooPlanningAgent.java
│   │   ├── ZooAssistantService.java
│   │   └── DecisionExplanationService.java
│   ├── planner/                       # CSP & Backtracking Solvers
│   │   ├── FeedingCSPService.java
│   │   ├── BacktrackingSolver.java
│   │   └── FeedingPlanningService.java
│   ├── config/                        # DataSeeder & Image Auto-Generator
│   ├── controller/                    # Controllers for 6 Pages
│   ├── entity/                        # 15 JPA Database Entities
│   ├── event/                         # Real-Time Terminal Event Loggers
│   ├── repository/                    # Spring Data JPA Repositories
│   └── service/                       # Business Logic & Dashboard Services
└── src/main/resources/
    ├── application.properties
    ├── static/images/animals/         # 13 Local Default PNG Images
    └── templates/                     # 6 Thymeleaf Views & ACME Sidebar Layout
```

---

## 🖥️ The 6 Application Pages

1. **Zookeeper Login** (`/login`): Demo authentication (`zookeeper` / `password`).
2. **Dashboard** (`/dashboard`): 8 dynamic stat cards & 6 sections calculated from SQLite.
3. **Animals & AI Knowledge Base** (`/animals`): Tab 1 (Animals grid with age & predefined local image picker) & Tab 2 (Species Knowledge Base).
4. **Today's Food & Observations** (`/food-observations`): Tab 1 (Food inventory), Tab 2 (Zookeeper shifts), Tab 3 (Observation entry).
5. **AI Feeding & Care Planner** (`/planner`): CSP Solver, Feeding schedule, Conflict resolution, Dynamic $A^*$ Re-Planning.
6. **AI Zoo Assistant** (`/assistant`): GPT-style chatbot interface grounded in SQLite.

---

## 🛠️ How to Run

1. Open terminal inside the project directory:
   ```bash
   cd C:\Users\91812\.gemini\antigravity\scratch\ai-zoo-planner
   ```
2. Execute Spring Boot Maven plugin:
   ```bash
   mvn spring-boot:run
   ```
3. Open your browser and navigate to:
   ```
   http://localhost:8080/login
   ```
4. Log in with:
   - **Username**: `zookeeper`
   - **Password**: `password`

---

## 🗄️ How to View `zoo.db` in DB Browser for SQLite

1. Download and install **DB Browser for SQLite** (from [sqlitebrowser.org](https://sqlitebrowser.org)).
2. Launch DB Browser for SQLite and click **Open Database**.
3. Select the file:
   `C:\Users\91812\.gemini\antigravity\scratch\ai-zoo-planner\zoo.db`
4. Browse tables: `animals`, `foods`, `species_knowledge`, `feeding_plans`, `feeding_tasks`, `observations`, `ai_decisions`.

---

## 🎓 AI Viva Questions & Explanation Guide

1. **How does Constraint Satisfaction (CSP) work in this system?**
   - **Variables**: Each animal feeding slot $(A_i, T_j)$.
   - **Domains**: Suitable foods inferred from `species_foods`, available stock, and on-duty zookeepers.
   - **Constraints**: Daily portion sufficiency, shift alignment, no overlapping keeper assignments at the same slot.

2. **How does A\* Search perform Dynamic Re-Planning?**
   - When a zookeeper becomes unavailable or a food stock depletes, $A^*$ explores states in a `PriorityQueue` using $f(n) = g(n) + h(n)$.
   - $g(n)$ counts past disruption penalties (changing keeper = +2, shifting time = +1, food substitution = +3).
   - $h(n)$ estimates remaining unassigned conflicts.

3. **Is the GPT Assistant inventing data?**
   - No. The `ZooAssistantService` extracts intent and queries SQLite `zoo.db` directly to return verified facts.
