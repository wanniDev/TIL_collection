package me.java.concurrency.a01_unsafe_counter;

import net.jcip.annotations.NotThreadSafe;

import javax.servlet.*;
import java.io.IOException;
import java.math.BigInteger;

@NotThreadSafe
public class UnsafeCountingFactorizer extends GenericServlet implements Servlet {
    private long count = 0;

    @Override
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        ++count;
        encodeIntoResponse(resp, factors);
    }

    void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
    }

    BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[] { i };
    }
}
