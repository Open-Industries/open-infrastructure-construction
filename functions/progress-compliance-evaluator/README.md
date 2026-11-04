# Progress Compliance Evaluator (serverless function)

Quarkus Funqy function on Knative (scale to zero). Compares defect counts and schedule adherence against project targets; one finding per breach.

Invoke: `POST /progressComplianceEvaluator` with the KPI payload.
