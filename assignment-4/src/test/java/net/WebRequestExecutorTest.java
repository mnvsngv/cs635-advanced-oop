package net;

import mock.MockUrlCreator;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class WebRequestExecutorTest {

    private static final long DATE_VALUE = 123L;


    @Test
    void getLastModifiedDateTest() throws IOException {
        HttpURLConnection httpMock = mock(HttpURLConnection.class);
        when(httpMock.getResponseCode()).thenReturn(200);
        when(httpMock.getLastModified()).thenReturn(DATE_VALUE);

        URL mockUrl = MockUrlCreator.createDefaultMockUrl(DATE_VALUE);

        long firstResponseDate = WebRequestExecutor.getLastModified(mockUrl);
        long secondResponseDate = WebRequestExecutor.getLastModified(mockUrl);

        assertEquals(0L, firstResponseDate);
        assertEquals(DATE_VALUE, secondResponseDate);
    }
}