# D&C Template

병합정렬의 사례를 보면, 분할정복은 일정한 패턴이 있다.
아래의 예시 코드를 통해, 분할 정복의 패턴에 대해 파악을 해보자

## Template
1. **Divide** : S를 n만큼 나눈다. : subproblems: {S1, S2, S3, ...Sn}
2. **Conquer** : 각 서브 문제를 푼다.
3. **Combine** : 각 서브 문제를 조합한다.
```python
def divide_and_conquer( S ):
    # (1). Divide the problem into a set of subproblems.
    [S1, S2, ... Sn] = divide(S)

    # (2). Solve the subproblem recursively,
    #   obtain the results of subproblems as [R1, R2... Rn].
    rets = [divide_and_conquer(Si) for Si in [S1, S2, ... Sn]]
    [R1, R2,... Rn] = rets

    # (3). combine the results from the subproblems.
    #   and return the combined result.
    return combine([R1, R2,... Rn])
```
위의 템플릿 코드를 보면, 분할정복의 핵심은 서브 문제와 본 문제와의 관계를 파악하여 재귀를 진행하여, 문제를 나누고 합하는 것에 있다.