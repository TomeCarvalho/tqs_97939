a)

![quality_gate.png](C:\Users\tomec\UA\TQS\Practical\tqs_97939\lab6\lab6_4\quality_gate.png)

In the context of the IES project, I decided to define the following conditions:

- No bugs (a stable project is preferred, even if it costs features, no bugs were found in the analysis)

- 20 or fewer code smells (acceptable due to time constraints, we had 56 of them but a lot of them are fixed in trivial ways, such as unused imports)

- No critical issues (as they could compromise the project)

- 10 or fewer major issues (to keep the project more robust)

- 2 or fewer vulnerabilities (we had 4, all related to the use of DTOs, or lack thereof, which we could have fixed, but due to time constraints we could accept one or two)

b)

![](C:\Users\tomec\AppData\Roaming\marktext\images\2022-04-11-15-59-09-image.png)

The conditions defined on the custom quality gate should already be enough to fail the overall code. To also fail the new increment, I added many unused imports and unused variables that also didn't follow naming conventions, resulting in 21 new code smells and 2 new critical issues.
