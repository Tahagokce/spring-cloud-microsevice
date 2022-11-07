package com.verseup.accountservice.modal;

import com.verseup.accountservice.modal.error.ErrorFields;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class GenericResponse<T>  {
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private ResponseMessage message;
    private List<ErrorFields> errors;
    private int responseStatus;
    private T data;
    private String errorMessageKey;
    private String errorMessage;
    private String stackTrace;


    public GenericResponse(ResponseMessage message, T data, List<ErrorFields> errors) {
        this.message = message;
        this.data = data;
        this.errors = errors;
    }

    public static <T> GenericResponse<T> empty() {
        return new GenericResponse<>();
    }


    public static <T> GenericResponse<T> ok() {
        return createGenericResponse(null, null, null, HttpStatus.OK.value(), ResponseMessage.ok(), null, null);
    }

    public static <T> GenericResponse<T> ok(Integer pageNumber, Integer pageSize, Long totalElements, T data) {
        return createGenericResponse(pageNumber, pageSize, totalElements, HttpStatus.OK.value(), ResponseMessage.ok(), data, null);
    }

    public static <T> GenericResponse<T> ok(T data) {
        return createGenericResponse(null, null, null, HttpStatus.OK.value(), ResponseMessage.ok(), data, null);
    }

    public static <T> GenericResponse<T> bad() {
        return createGenericResponse(null, null, null, HttpStatus.BAD_REQUEST.value(), ResponseMessage.bad(), null, null);
    }

    public static <T> GenericResponse<T> bad(Throwable ex, String key) {
        GenericResponse r = createGenericResponse(null, null, null, HttpStatus.BAD_REQUEST.value(), ResponseMessage.bad(), null, null);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        r.setStackTrace(sw.toString());
        r.setErrorMessageKey(key);
        r.setErrorMessage(ex.getMessage());
        return r;
    }

    public static <T> GenericResponse<T> resourceNotFound() {
        return createGenericResponse(null, null, null, HttpStatus.NOT_FOUND.value(), ResponseMessage.resourceNotFound(), null, null);
    }

    public static <T> GenericResponse<T> withError(List<ErrorFields> errors) {
        return createGenericResponse(null, null, null, HttpStatus.NOT_ACCEPTABLE.value(), ResponseMessage.error(), null, errors);
    }

    public static <T> GenericResponse<T> fromPageableData(PageableData<?> pageableData, T data) {
        return GenericResponse.ok(pageableData.getPageNumber(), pageableData.getPageSize(), pageableData.getTotalElements(), data);
    }

    private static <T> GenericResponse<T> createGenericResponse(Integer pageNumber,
                                                                Integer pageSize,
                                                                Long totalElements,
                                                                int responseStatus,
                                                                ResponseMessage message,
                                                                T data,
                                                                List<ErrorFields> errors) {
        GenericResponse<T> genericResponse = new GenericResponse<>(message, data, errors);
        genericResponse.setPageNumber(pageNumber);
        genericResponse.setPageSize(pageSize);
        genericResponse.setTotalElements(totalElements);
        genericResponse.setResponseStatus(responseStatus);
        return genericResponse;
    }
}
