# UI Agent Workflow Specification

**Status:** Draft --- Phase 1\
**Purpose:** Define the production workflow, responsibilities,
contracts, approval model, and artifact lifecycle for AI-assisted UI/UX
development.

------------------------------------------------------------------------

## 1. Purpose

This framework exists to allow the AI team to design and implement
application UI/UX without requiring the user to manually design layouts
or make routine visual decisions.

The system is responsible for producing UI that is:

1.  Consistent --- the application should look as if it was designed by
    one person.
2.  Reusable --- existing components and patterns must be reused
    whenever possible.
3.  UX-first --- flows and usability take priority over visual novelty.
4.  Maintainable --- the system must scale without progressively
    degrading.
5.  Fast --- once the design decisions are delegated, implementation
    should move efficiently.
6.  Context-efficient --- agents should receive the minimum useful
    context while retaining access to the repository for verification.

The user acts primarily as the decision-maker for important product and
design decisions. Agents own routine design, implementation, review, and
iteration decisions.

------------------------------------------------------------------------

## 2. Core Principles

### 2.1 AI owns UI/UX decisions

The user describes the desired functionality and constraints.

The AI team is responsible for:

-   UX flows
-   information architecture
-   navigation
-   visual hierarchy
-   component selection
-   component creation
-   design tokens
-   responsive behaviour
-   accessibility
-   implementation structure
-   visual consistency

The system should not routinely ask the user how a screen should be
designed.

### 2.2 The user approves plans, not implementation details

Before implementation begins, the Orchestrator must present a complete
feature plan to the user.

The plan must be understandable without requiring the user to inspect
internal agent discussions.

The user may refine, reject, or modify the plan.

Implementation cannot begin until the user explicitly approves it.

Approval applies to the intended outcome and architectural direction,
not every implementation detail.

### 2.3 Approved plans are adaptable

An approved plan is not an immutable implementation script.

Agents may make minor implementation decisions without asking the user
again when those decisions:

-   preserve the approved UX;
-   preserve the approved architecture;
-   do not materially change the Design System;
-   do not affect existing behaviour unexpectedly;
-   do not introduce significant scope or complexity.

The Orchestrator must escalate only material decisions.

### 2.4 Simplicity over unnecessary sophistication

The system should prefer the smallest solution that satisfies the
requirements.

Avoid:

-   unnecessary abstractions;
-   speculative components;
-   premature generalisation;
-   unnecessary dependencies;
-   duplicate patterns;
-   architecture created only for hypothetical future requirements.

### 2.5 The Design System is the visual source of truth

All UI must conform to the application's Design System.

No agent may arbitrarily introduce visual patterns outside it.

The Design System may evolve when a feature genuinely requires a new
pattern.

### 2.6 The Design System is living

The Design System is expected to grow with the application.

When a feature requires a capability that does not exist, the Design
System Architect decides whether to:

1.  reuse an existing component;
2.  extend an existing component;
3.  create a new Foundation Component;
4.  create a new Application Component.

Such changes are part of the feature plan and do not require separate
user approval.

------------------------------------------------------------------------

## 3. Priority Order

When trade-offs are necessary, use this order:

1.  Consistency
2.  Reusability
3.  UX quality
4.  Maintainability
5.  Development speed
6.  Context/token efficiency

A lower-priority goal must never compromise a higher-priority goal
without an explicit material decision being escalated to the user.

------------------------------------------------------------------------

## 4. Unit of Work

The primary unit of work is a **Feature**.

A Feature can represent:

-   a large capability spanning multiple screens;
-   a single screen;
-   a single interaction;
-   a small UI change;
-   a new reusable component when that component is the actual requested
    outcome.

The pipeline must remain flexible enough that a Feature does not imply a
fixed number of screens or implementation tasks.

A Feature owns its UX, UI, Design System changes, implementation, and
review lifecycle.

------------------------------------------------------------------------

## 5. High-Level Workflow

``` text
User Request
    |
    v
Orchestrator
    |
    v
Discovery / Repository Inspection
    |
    v
Product & UX Architect
    |
    v
Design System Architect
    |
    v
UI Designer
    |
    v
Compose Architect
    |
    +----> Component Builder
    |
    v
Compose Developer
    |
    v
Design System Guardian
    |
    v
Orchestrator Review
    |
    +----> correction loop
    |
    v
Feature Complete
```

The exact ordering may be adapted when a feature does not require every
stage.

The Orchestrator owns the workflow and decides which specialists are
required.

------------------------------------------------------------------------

## 6. Human Approval Gate

The Orchestrator must produce a plan before any implementation changes
are made.

The plan must contain, at minimum:

### 6.1 Feature

-   objective;
-   user-facing outcome;
-   scope.

### 6.2 UX

-   primary flow;
-   relevant secondary flows;
-   navigation;
-   important states;
-   important edge cases.

### 6.3 Design System

-   new Foundation Components;
-   new Application Components;
-   existing components to reuse;
-   meaningful token or pattern changes.

### 6.4 Screens

-   screens being added or changed;
-   purpose of each;
-   important interaction decisions.

### 6.5 Implementation

-   major implementation areas;
-   architecture implications;
-   navigation/state implications.

### 6.6 Review

-   validation stages;
-   important acceptance criteria.

The plan should be detailed enough for the user to identify an unwanted
product or design decision, but not so detailed that it becomes an
implementation transcript.

Only the explicit approval signal defined by the Orchestrator counts as
approval.

------------------------------------------------------------------------

## 7. Material Decision Escalation

The Orchestrator must continue autonomously for routine decisions.

Escalation is required when a decision materially changes the approved
plan.

Examples include:

-   changing the primary UX flow;
-   introducing a significant new screen;
-   removing a public Design System component;
-   changing an established visual pattern across the application;
-   introducing a significant architectural dependency;
-   breaking existing functionality;
-   materially increasing feature scope;
-   choosing between alternatives with important long-term consequences;
-   discovering a requirement contradiction that cannot be safely
    resolved by assumption.

Non-material decisions must not block the workflow.

Examples:

-   changing internal composable decomposition;
-   selecting one equivalent Material 3 implementation;
-   adjusting spacing within established tokens;
-   fixing an implementation issue without changing the intended UX;
-   extracting an implementation detail into a private composable;
-   correcting accessibility issues.

------------------------------------------------------------------------

## 8. Repository Access Model

The framework uses a **hybrid context model**.

Agents have access to the repository so they can inspect:

-   existing architecture;
-   source code;
-   existing components;
-   Design System implementation;
-   navigation;
-   tests;
-   conventions;
-   dependencies;
-   current project state.

However, the repository is not the agent's primary specification.

The relevant pipeline artifacts define the intended work.

Therefore:

> Agents may inspect the repository to understand reality, but must use
> pipeline artifacts as the source of truth for the current task.

This prevents both blind implementation and uncontrolled
reinterpretation.

------------------------------------------------------------------------

## 9. Artifact-Driven Communication

Agents communicate primarily through persistent Markdown artifacts
rather than relying on conversational context.

Artifacts provide:

-   stable contracts;
-   traceability;
-   reduced context requirements;
-   reproducibility;
-   easier review;
-   a persistent record of design decisions.

The Orchestrator is responsible for ensuring that the correct artifact
exists before delegating work that depends on it.

------------------------------------------------------------------------

## 10. Artifact Ownership

Each artifact has a primary owner.

  ----------------------------------------------------------------------------
  Artifact                     Owner                   Main Consumers
  ---------------------------- ----------------------- -----------------------
  `feature-spec.md`            Product & UX Architect  Design System
                                                       Architect, UI Designer,
                                                       Orchestrator

  `design-system.md`           Design System Architect UI Designer, Component
                                                       Builder, Compose
                                                       Architect, Developer,
                                                       Guardian

  `screen-spec.md`             UI Designer             Compose Architect,
                                                       Developer, Guardian

  `component-catalog.md`       Design System Architect All UI agents
                               / Component Builder     

  `compose-plan.md`            Compose Architect       Compose Developer,
                                                       Guardian

  `implementation-report.md`   Compose Developer       Orchestrator, Guardian

  `review-report.md`           Guardian                Orchestrator, Developer
  ----------------------------------------------------------------------------

An agent may consume another agent's artifact but must not silently
redefine its responsibilities.

------------------------------------------------------------------------

## 11. Design System Architecture

The Design System has two conceptual layers.

### 11.1 Foundation Components

Foundation Components wrap and customise Material 3 primitives.

Examples:

``` text
AppButton
AppCard
AppTextField
AppChip
AppDialog
AppTopBar
AppNavigationBar
AppFAB
```

They own the application's:

-   colours;
-   typography;
-   dimensions;
-   shape;
-   elevation;
-   interaction states;
-   accessibility defaults;
-   animation conventions.

Screens should not directly use Material 3 primitives when an
application Foundation Component exists.

### 11.2 Application Components

Application Components combine Foundation Components into reusable
patterns specific to the application.

Examples:

``` text
PlayerCard
GameCard
ScoreChip
RoundSummary
GameHeader
PlayerScore
```

They may understand application/domain concepts, but they must remain
reusable across appropriate screens.

### 11.3 Screens

Screens compose Application Components and Foundation Components.

Screens should contain feature-specific composition and behaviour, not
duplicate Design System implementations.

------------------------------------------------------------------------

## 12. Design System Evolution

When a required UI capability does not exist:

``` text
Feature requirement
    |
    v
Design System Architect
    |
    +--> reuse existing component
    |
    +--> extend existing component
    |
    +--> create Foundation Component
    |
    +--> create Application Component
```

The decision must be reflected in the feature plan.

New components become part of the application's persistent Design System
knowledge.

A new component must not be created merely because an existing component
could be reused or appropriately extended.

------------------------------------------------------------------------

## 13. Component Implementation Rules

Foundation and Application Components must have a clear public purpose.

A component should be created when at least one of the following is
true:

-   it represents an established application-wide visual pattern;
-   it is reused across multiple screens or is expected to be reused;
-   it centralises a meaningful design decision;
-   it prevents direct Material 3 usage from spreading through the
    application;
-   it represents a meaningful application-specific pattern.

Avoid creating wrappers that provide no meaningful abstraction.

The Component Builder owns implementation of Design System components.

The Compose Developer consumes them.

------------------------------------------------------------------------

## 14. Agent Responsibilities

### 14.1 Orchestrator

Owns the entire workflow.

Responsibilities:

-   understand the user request;
-   inspect the repository or delegate discovery;
-   coordinate specialists;
-   resolve conflicts between specialist outputs;
-   create and present the feature plan;
-   obtain approval;
-   delegate implementation;
-   manage review loops;
-   escalate material decisions;
-   ensure artifacts remain coherent;
-   decide when the feature is complete.

The Orchestrator does not implement application code.

### 14.2 Product & UX Architect

Owns:

-   user goals;
-   feature flows;
-   information architecture;
-   navigation;
-   states;
-   edge cases;
-   UX decisions.

Does not implement UI.

### 14.3 Design System Architect

Owns:

-   Design System architecture;
-   design tokens;
-   Foundation Components;
-   Application Components;
-   component reuse;
-   component creation decisions;
-   component variants;
-   evolution of the visual language.

Does not implement screen features.

### 14.4 UI Designer

Owns:

-   visual hierarchy;
-   screen composition;
-   interaction presentation;
-   responsive behaviour;
-   visual states;
-   accessibility presentation.

Does not invent Design System components independently.

### 14.5 Component Builder

Owns implementation of approved Design System components.

Does not redesign components or create screen-specific alternatives
without direction from the Design System Architect.

### 14.6 Compose Architect

Owns:

-   composable decomposition;
-   state ownership;
-   event boundaries;
-   screen architecture;
-   reuse boundaries;
-   integration structure.

Does not change the approved UX.

### 14.7 Compose Developer

Owns implementation of feature screens and integration.

Must reuse the Design System.

Must not bypass established components merely for convenience.

### 14.8 Design System Guardian

Owns visual and architectural consistency review.

Checks:

-   direct Material 3 usage where an App component exists;
-   duplicated UI patterns;
-   inconsistent tokens;
-   unnecessary component creation;
-   violations of Design System contracts;
-   divergence from screen specifications;
-   accessibility issues;
-   maintainability concerns.

A rejection must contain actionable findings.

------------------------------------------------------------------------

## 15. Review Loop

The default implementation loop is:

``` text
Developer
    |
    v
Guardian
    |
    +--> PASS --> Orchestrator
    |
    +--> REJECT
             |
             v
        Orchestrator
             |
             v
        Developer
             |
             v
        Guardian
```

The loop continues without user intervention for non-material
corrections.

The user is contacted only when resolving the issue requires a material
change to the approved plan.

------------------------------------------------------------------------

## 16. Artifact Lifecycle

Artifacts are persistent during the project.

Recommended structure:

``` text
misc/
└── ui/
    ├── workflow-spec.md
    ├── design-system.md
    ├── component-catalog.md
    ├── agents/
    │   ├── orchestrator.md
    │   ├── product-ux-architect.md
    │   ├── design-system-architect.md
    │   ├── ui-designer.md
    │   ├── component-builder.md
    │   ├── compose-architect.md
    │   ├── compose-developer.md
    │   └── design-system-guardian.md
    │
    ├── artifacts/
    │   ├── feature-spec.md          (template)
    │   ├── screen-spec.md           (template)
    │   ├── compose-plan.md          (template)
    │   ├── implementation-report.md (template)
    │   └── review-report.md         (template)
    │
    └── feature/
        └── feature-<feature-name>/
            ├── feature-spec.md
            ├── screen-spec.md
            ├── compose-plan.md
            ├── implementation-report.md
            └── reviews/
                └── review-report.md
```

Templates live in `artifacts/` and are never edited. Generated
feature artifacts live in `feature/feature-<feature-name>/`. Global
project-level artifacts (`design-system.md`, `component-catalog.md`)
live directly in `misc/ui/`.

The project may choose not to commit generated feature artifacts.

However, persistent Design System knowledge should have a stable
canonical location so future agents can recover it.

------------------------------------------------------------------------

## 17. Source of Truth Hierarchy

When information conflicts, use this hierarchy:

1.  Current explicit user decision
2.  Approved feature plan
3.  Canonical Design System
4.  Feature artifacts
5.  Existing repository implementation
6.  Agent assumptions

An agent must not silently resolve a conflict by choosing a
lower-priority source.

If the conflict materially affects the plan, the Orchestrator resolves
it.

------------------------------------------------------------------------

## 18. Context Rules

Agents should receive enough context to perform their role correctly,
but no more than necessary.

Every agent should:

1.  inspect relevant repository state;
2.  read the artifacts required by its role;
3.  avoid reading unrelated feature history unless necessary;
4.  treat canonical Design System documentation as persistent context;
5.  write only the artifacts or source areas within its responsibility.

Context minimisation is an efficiency goal, not a correctness goal.

When additional context is necessary to avoid an incorrect decision,
correctness wins.

------------------------------------------------------------------------

## 19. Idempotency and Existing Work

Before creating or modifying anything, agents must inspect the current
repository.

If a component, screen, pattern, or implementation already exists:

-   reuse it when appropriate;
-   extend it when appropriate;
-   avoid creating a duplicate.

Agents must account for uncommitted changes and existing implementation
state.

They must not overwrite unrelated user work.

------------------------------------------------------------------------

## 20. Completion Criteria

A Feature is complete only when:

-   the approved UX is implemented;
-   the relevant Design System changes are implemented;
-   the intended screens and interactions are implemented;
-   existing components are reused appropriately;
-   no avoidable direct Material 3 usage bypasses the Design System;
-   the Guardian has no unresolved blocking findings;
-   implementation and artifacts are coherent;
-   no material unresolved decision remains.

The Orchestrator makes the final completion determination.

------------------------------------------------------------------------

## 21. Non-Goals

This framework is not intended to:

-   replace product strategy;
-   autonomously redefine the application's business requirements;
-   optimise every line of code prematurely;
-   create a component for every one-off UI element;
-   force every feature through every agent;
-   require user approval for routine implementation decisions;
-   guarantee pixel-perfect visual output without validation;
-   treat Material 3 as the application's final visual identity.

Material 3 is the foundation, not the application's design.

------------------------------------------------------------------------

## 22. Required Agent Frontmatter

Every agent must define frontmatter describing its role and
capabilities.

At minimum:

``` yaml
---
description: <concise responsibility>
mode: <primary | subagent>
model: <model identifier>
temperature: <value>
tools:
  <tool>: <true | false>
---
```

Frontmatter must reflect actual capabilities.

An agent must not be given write, edit, shell, or other capabilities
that are unnecessary for its role.

The exact frontmatter of each production agent will be defined during
Phase 2 and Phase 3.

------------------------------------------------------------------------

## 23. Production Safety Rules

Agents must:

-   never claim an implementation was completed without verifying it;
-   never overwrite unrelated work;
-   never bypass an existing Design System component without
    justification;
-   never silently change an approved UX flow;
-   never hide a material deviation from the Orchestrator;
-   never treat generated artifacts as more authoritative than explicit
    user decisions;
-   never ask the user to resolve routine implementation details.

The Orchestrator is responsible for ensuring these rules are respected
across the team.

------------------------------------------------------------------------

## 24. Phase Boundaries

The framework will be built in four phases:

### Phase 1 --- Workflow Specification

Define:

-   workflow;
-   responsibilities;
-   source-of-truth rules;
-   approval model;
-   artifact contracts;
-   review model.

### Phase 2 --- Orchestrator

Define the production coordinator that:

-   manages discovery;
-   coordinates agents;
-   generates the plan;
-   obtains approval;
-   delegates work;
-   manages review loops;
-   escalates material decisions.

### Phase 3 --- Specialist Agents

Define all specialist agents with:

-   production frontmatter;
-   role;
-   responsibilities;
-   constraints;
-   inputs;
-   outputs;
-   artifact ownership;
-   escalation rules.

### Phase 4 --- Artifact Templates

Define the canonical templates for all inter-agent documents.

The templates must be concrete enough that agents can reliably consume
one another's output without relying on hidden conversational context.

------------------------------------------------------------------------

## 25. Design Goal

The final system should make the normal development experience look
approximately like this:

``` text
User:

"Add statistics to the game."

        ↓

Orchestrator:

inspects project
asks only necessary questions
coordinates specialists

        ↓

Specialists:

design UX
design visual language
identify/create components
design screens
plan implementation

        ↓

Orchestrator:

presents plan

        ↓

User:

approved

        ↓

Agents:

implement
review
correct
review again

        ↓

Orchestrator:

reports completion
```

The user should spend their attention on product decisions and
meaningful trade-offs, not on manually specifying layouts, padding,
colours, buttons, or composable structure.
