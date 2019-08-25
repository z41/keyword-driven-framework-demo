Written from scratch in 2 days. Please do not use it :)


Some things were almost new to me: Lombok, Guice, Keyword-driven / Hybrid approach.

Features:

- integrates with TestNG and generates tests from Excel file on-fly (run ExcelSuiteFactory if you use IntelliJ)
- DI-container is used to instantiate all objects. It's possible to create as much modules with different configurations as you wish
- properties-based kind of page-objects
- possible to use element collections and filter them to use in subsequent steps (see result.apply) 
- possible to extend with custom UI components
- possible to use not only with UI (add more modules and handlers!)

What's bad:
- full of hacks
- lack of error handling in many cases
- lack of logging
- not well-thought 
- probably over-complicated

But still good for demonstration of Keyword-driven / Hybrid test frameworks and their (dis)-advantages.
