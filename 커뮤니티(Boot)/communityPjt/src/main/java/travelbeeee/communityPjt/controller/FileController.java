package travelbeeee.communityPjt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import travelbeeee.communityPjt.domain.UploadFile;
import travelbeeee.communityPjt.service.FileService;
import travelbeeee.communityPjt.service.WritingService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Controller @RequiredArgsConstructor
public class FileController {

    private final WritingService writingService;
    private final FileService fileService;

    @GetMapping("/uploadFile/download/{uploadFileId}")
    public void fileDownload(@PathVariable("uploadFileId") Long uploadFileId, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
        UploadFile uploadFile = fileService.getUploadFileById(uploadFileId);
        String extensionName = uploadFile.getOriginFileName();
        // 원본 이름에서 . 이후로 확장자만 잘라오기
        for(int i = 0; i < extensionName.length(); i++)
            if(extensionName.charAt(i) == '.')
                extensionName = extensionName.substring(i);

        String fileName = uploadFile.getChangedFileName() + extensionName;

        File file = fileService.fileDownload(uploadFileId);

        InputStream is = null;
        OutputStream os = null;

        String mimetype = "application/x-msdownload";
        response.setContentType(mimetype);

        try {
            setDisposition(fileName, request, response);
            is = new FileInputStream(file);
            os = response.getOutputStream();

            byte b[] = new byte[(int) file.length()];
            int leng = 0;

            while((leng = is.read(b)) > 0){
                os.write(b,0,leng);
            }

            is.close();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return;
    }

    static void setDisposition(String filename, HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        String browser = getBrowser(request);
        String dispositionPrefix = "attachment; filename=";
        String encodedFilename = null;

        if (browser.equals("MSIE")) {
            encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll(
                    "\\+", "%20");
        } else if (browser.equals("Firefox")) {
            encodedFilename = "\""
                    + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
        } else if (browser.equals("Opera")) {
            encodedFilename = "\""
                    + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
        } else if (browser.equals("Chrome")) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < filename.length(); i++) {
                char c = filename.charAt(i);
                if (c > '~') {
                    sb.append(URLEncoder.encode("" + c, "UTF-8"));
                } else {
                    sb.append(c);
                }
            }
            encodedFilename = sb.toString();
        } else {
            throw new IOException("Not supported browser");
        }

        response.setHeader("Content-Disposition", dispositionPrefix
                + encodedFilename);

        if ("Opera".equals(browser)) {
            response.setContentType("application/octet-stream;charset=UTF-8");
        }

    }

    static public String getBrowser(HttpServletRequest request) {
        String header = request.getHeader("User-Agent");
        if (header.indexOf("MSIE") > -1) {
            return "MSIE";
        } else if (header.indexOf("Chrome") > -1) {
            return "Chrome";
        } else if (header.indexOf("Opera") > -1) {
            return "Opera";
        } else if (header.indexOf("Firefox") > -1) {
            return "Firefox";
        } else if (header.indexOf("Mozilla") > -1) {
            if (header.indexOf("Firefox") > -1) {
                return "Firefox";
            }else{
                return "MSIE";
            }
        }
        return "MSIE";
    }
}
