# System Design Questions for Congestion Charging

## 1. System Scale and Architecture
- What is the expected number of requests per day/hour?
- How many concurrent users or vehicles do we anticipate?
- Should we have monolith or microservices here?

## 2. Rules and Charges

- Is the single charge rule specific to Gothenburg, or should it be configurable in code?
- Are there other rules that should be configurable? Like the tax not being calculated on weekends, public holidays etc. 
- Are there any other possibilities of charging? Every pass, shorter timespan than 1 hour?
- Is this code supposed to work for the cities outside of Sweden? If so, we should include countries for currency and holiday definitions

## 3. Vehicle Identification

- Should the system track specific users, or rely solely on vehicle-based identification?
- How do we handle shared vehicles?
- How do we handle vehicles who pass the 
