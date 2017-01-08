package com.darren.personal.listener;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

import com.darren.personal.websocket.UploadWebSocketServer;

@Component
public class FileUploadProgressListener implements ProgressListener {
    private HttpSession session;

    public void setSession(HttpSession session) {
        this.session = session;
    }

    /**
     * @param currentBytesRead
     *            current read file bytes
     * @param currentContentLength
     *            current read file content length: file total size
     * @param currentFileItem
     *            current read file item
     */
    @Override
    public void update(long currentBytesRead, long currentContentLength, int currentFileItem) {
        UploadWebSocketServer server = UploadWebSocketServer.getSocketServer(this.session.getId());
        int percentage = (int) (((double)currentBytesRead / currentContentLength) * 100);
        
        if (server != null) {
            server.sendMessage(String.valueOf(percentage));
        }
    }

}
