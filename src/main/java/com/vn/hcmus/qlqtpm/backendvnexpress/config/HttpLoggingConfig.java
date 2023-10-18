package com.vn.hcmus.qlqtpm.backendvnexpress.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.output.TeeOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;


@WebFilter("/actuator/*")
public class HttpLoggingConfig implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(HttpLoggingConfig.class);

    private static boolean startsWith(final String string, final String... possibilities) {
        for (String prefix : possibilities) {
            if (string.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;

            Map<String, String> requestMap = this
                    .getTypesafeRequestMap(httpServletRequest);
            BufferedRequestWrapper bufferedRequest = new BufferedRequestWrapper(
                    httpServletRequest);
            BufferedResponseWrapper bufferedResponse = new BufferedResponseWrapper(
                    httpServletResponse);


            chain.doFilter(bufferedRequest, bufferedResponse);
            String[] prefixes = {"/actuator/", "/webjars/", "/swagger-resources", "/swagger-ui", "/v2/api-docs", "/swagger-ui.html"};
            if (!startsWith(((HttpServletRequest) request).getRequestURI(), prefixes)) {
                ObjectMapper objectMapper = new ObjectMapper();
                LogRequest logRequest = LogRequest.builder()
                        .method(httpServletRequest.getMethod())
                        .path(httpServletRequest.getServletPath())
                        .requestParams(objectMapper.writeValueAsString(requestMap))
                        .requestBody(bufferedRequest.getRequestBody())
                        .ipAddress(httpServletRequest.getRemoteAddr())
                        .uri(httpServletRequest.getRequestURI())
                        .response(bufferedResponse.getContent())
                        .build();
                logger.info(objectMapper.writeValueAsString(logRequest));
            }

        } catch (Throwable a) {
            logger.info(a.toString());
        }
    }

    @Override
    public void destroy() {

    }

    private Map<String, String> getTypesafeRequestMap(HttpServletRequest request) {
        Map<String, String> typesafeRequestMap = new HashMap<>();
        Enumeration<?> requestParamNames = request.getParameterNames();
        while (requestParamNames.hasMoreElements()) {
            String requestParamName = (String) requestParamNames.nextElement();
            String requestParamValue;
            if (requestParamName.equalsIgnoreCase("password")) {
                requestParamValue = "********";
            } else {
                requestParamValue = request.getParameter(requestParamName);
            }
            typesafeRequestMap.put(requestParamName, requestParamValue);
        }
        return typesafeRequestMap;
    }


    private static final class BufferedRequestWrapper extends
            HttpServletRequestWrapper {
        private byte[] buffer = null;

        public BufferedRequestWrapper(HttpServletRequest req)
                throws IOException {
            super(req);
            InputStream is = req.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int read;
            while ((read = is.read(buf)) > 0) {
                outputStream.write(buf, 0, read);
            }
            this.buffer = outputStream.toByteArray();
        }

        @Override
        public ServletInputStream getInputStream() {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(this.buffer);
            return new BufferedServletInputStream(inputStream);
        }

        String getRequestBody() throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    this.getInputStream()));
            String line = null;
            StringBuilder inputBuffer = new StringBuilder();
            do {
                line = reader.readLine();
                if (null != line) {
                    inputBuffer.append(line.trim());
                }
            } while (line != null);
            reader.close();
            return inputBuffer.toString().trim();
        }

    }

    private static final class BufferedServletInputStream extends
            ServletInputStream {

        private final ByteArrayInputStream bais;

        public BufferedServletInputStream(ByteArrayInputStream bais) {
            this.bais = bais;
        }

        @Override
        public int available() {
            return this.bais.available();
        }

        @Override
        public int read() {
            return this.bais.read();
        }

        @Override
        public int read(byte[] buf, int off, int len) {
            return this.bais.read(buf, off, len);
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }
    }

    public static class TeeServletOutputStream extends ServletOutputStream {

        private final TeeOutputStream targetStream;

        public TeeServletOutputStream(OutputStream one, OutputStream two) {
            targetStream = new TeeOutputStream(one, two);
        }

        @Override
        public void write(int arg0) throws IOException {
            this.targetStream.write(arg0);
        }

        public void flush() throws IOException {
            super.flush();
            this.targetStream.flush();
        }

        public void close() throws IOException {
            super.close();
            this.targetStream.close();
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {

        }
    }

    public static class BufferedResponseWrapper implements HttpServletResponse {

        HttpServletResponse response;
        TeeServletOutputStream teeServletOutputStream;
        ByteArrayOutputStream outputStream;

        public BufferedResponseWrapper(HttpServletResponse response) {
            this.response = response;
        }

        public String getContent() {
            return outputStream.toString();
        }

        public PrintWriter getWriter() throws IOException {
            return response.getWriter();
        }

        public ServletOutputStream getOutputStream() throws IOException {
            if (teeServletOutputStream == null) {
                outputStream = new ByteArrayOutputStream();
                teeServletOutputStream = new TeeServletOutputStream(response.getOutputStream(),
                        outputStream);
            }
            return teeServletOutputStream;

        }

        @Override
        public String getCharacterEncoding() {
            return response.getCharacterEncoding();
        }

        @Override
        public void setCharacterEncoding(String charset) {
            response.setCharacterEncoding(charset);
        }

        @Override
        public String getContentType() {
            return response.getContentType();
        }

        @Override
        public void setContentType(String type) {
            response.setContentType(type);
        }

        @Override
        public void setContentLength(int len) {
            response.setContentLength(len);
        }

        @Override
        public void setContentLengthLong(long l) {
            response.setContentLengthLong(l);
        }

        @Override
        public int getBufferSize() {
            return response.getBufferSize();
        }

        @Override
        public void setBufferSize(int size) {
            response.setBufferSize(size);
        }

        @Override
        public void flushBuffer() throws IOException {
            teeServletOutputStream.flush();
        }

        @Override
        public void resetBuffer() {
            response.resetBuffer();
        }

        @Override
        public boolean isCommitted() {
            return response.isCommitted();
        }

        @Override
        public void reset() {
            response.reset();
        }

        @Override
        public Locale getLocale() {
            return response.getLocale();
        }

        @Override
        public void setLocale(Locale loc) {
            response.setLocale(loc);
        }

        @Override
        public void addCookie(Cookie cookie) {
            response.addCookie(cookie);
        }


        @Override
        public boolean containsHeader(String name) {
            return response.containsHeader(name);
        }

        @Override
        public String encodeURL(String url) {
            return response.encodeURL(url);
        }

        @Override
        public String encodeRedirectURL(String url) {
            return response.encodeRedirectURL(url);
        }

        @SuppressWarnings("deprecation")
        @Override
        public String encodeUrl(String url) {
            return response.encodeUrl(url);
        }

        @SuppressWarnings("deprecation")
        @Override
        public String encodeRedirectUrl(String url) {
            return response.encodeRedirectUrl(url);
        }

        @Override
        public void sendError(int sc, String msg) throws IOException {
            response.sendError(sc, msg);
        }

        @Override
        public void sendError(int sc) throws IOException {
            response.sendError(sc);
        }

        @Override
        public void sendRedirect(String location) throws IOException {
            response.sendRedirect(location);
        }

        @Override
        public void setDateHeader(String name, long date) {
            response.setDateHeader(name, date);
        }

        @Override
        public void addDateHeader(String name, long date) {
            response.addDateHeader(name, date);
        }

        @Override
        public void setHeader(String name, String value) {
            response.setHeader(name, value);
        }

        @Override
        public void addHeader(String name, String value) {
            response.addHeader(name, value);
        }

        @Override
        public void setIntHeader(String name, int value) {
            response.setIntHeader(name, value);
        }

        @Override
        public void addIntHeader(String name, int value) {
            response.addIntHeader(name, value);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void setStatus(int sc, String sm) {
            response.setStatus(sc, sm);
        }

        @Override
        public String getHeader(String arg0) {
            return response.getHeader(arg0);
        }

        @Override
        public Collection<String> getHeaderNames() {
            return response.getHeaderNames();
        }

        @Override
        public Collection<String> getHeaders(String arg0) {
            return response.getHeaders(arg0);
        }

        @Override
        public int getStatus() {
            return response.getStatus();
        }

        @Override
        public void setStatus(int sc) {
            response.setStatus(sc);
        }

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LogRequest {
        private String method;
        private String path;
        private String requestParams;
        private String requestBody;
        private String ipAddress;
        private String uri;
        private String response;
    }
}
