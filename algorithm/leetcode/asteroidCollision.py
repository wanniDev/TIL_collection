class Solution(object):
    def asteroidCollision(self, asteroids):
        """
        :type asteroids: List[int]
        :rtype: List[int]
        """
        stack = []
        i = 0

        while i < len(asteroids):
            asteroid = asteroids[i]
            if not stack or asteroid > 0 or stack[-1] < 0:
                stack.append(asteroid)
            else:
                if stack[-1] == -asteroid:
                    stack.pop()
                elif stack[-1] < -asteroid:
                    stack.pop()
                    i -= 1
            i += 1
        return stack
