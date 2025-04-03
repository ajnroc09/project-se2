@ControllerAdvice
public class GlobalExceptionHandler {
	// xu ly loi ko tim thay course
	@ExceptionHandler(CourseNotFoundException.class)
	public ResponseEntity<Object> handleCompanyNotFoundException(CourseNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	// xu ly loi ko tim thay student
	@ExceptionHandler(StudentNotFoundException.class)
	public ResponseEntity<Object> handleEmployeeNotFoundException(StudentNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
	}


	//khi ko co exceptionHandler cua exception xay ra thi se dc xu ly boi 1 trong 2 exception sau:

	// xu ly loi chung (RuntimeException)
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Object> handlerRuntimeException (RuntimeException exception, WebRequest request) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", "Co loi xay ra, vui long thu lai!");

		return new ResponseEntity<>(body,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// xu ly loi chung (Exception)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGlobalException(Exception exception,  WebRequest request) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message",exception.getMessage());

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}


}
