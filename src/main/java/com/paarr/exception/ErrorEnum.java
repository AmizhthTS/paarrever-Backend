package com.paarr.exception;

public enum ErrorEnum {

	USER_ALREADY_EXISTS(1001, "User Already Exists"), INVALID_EMAIL(1002, "Invalid Email"),
	INVALID_PHONE(1003, "Invalid Phone Number"), INVALID_NAME(1004, "Invalid Name"),
	FIRSTNAME_OR_STARTUPNAME_REQUIRED(1005, "First Name or Startup Name is Required"),
	LOCKED_ACCOUNT(1006, "Account Locked, Kindly use forgot password to unlock the account"),
	INVALID_FILE_SIZE(1007, "File size should be lesser than mentioned size"),
	USER_ALREADY_REMOVED(1008, "User Already Removed"), EMAIL_ALREADY_EXISTS(1009, "Email Already Exists"),
	INVALID_OLD_PASSWORD(1010, "Invalid Old Password"), OTP_INVALID(1011, "Invalid Otp"),
	OTP_EXPIRED(1012, "Otp Expired"), INVALID_EVENT_NAME(1013, "Invalid Event Name"),
	FILE_UPLOAD_FAILED(1014, "File Upload Failed"), PERSONA_MANDATORY(1015, "Please Select a User"),
	GOOGLE_NOTREACHABLE(1016, "Unable to reach google server please try again later"),
	INVALID_CREDINTIALS(1017, "Incorrect Username or Password"),
	INVALID_PASSWORD(1018, "Incorrect Password, Please use Forgot Password to reset your password"),
	INVALID_TYPE(1019, "Invalid Type"), INVALID_PERIOD(1020, "Please select a period of time"),
	INVALID_PAN(1021, "Invalid PAN"), INVALID_CIN(1022, "Invalid CIN"), INVALID_DPIIT(1023, "Invalid DPIIT Number"),
	PAN_ALREADY_EXISTS(1024, "PAN Number already registered in different account"),
	INVALID_USERNAME(1025, "User name does not exists, kindly register"),
	ORGANIZATION_ALREADY_EXISTS(1026, "Organization already registered in different email"),
	INVALID_OFFERTYPE(1027, "Offer type not found"),
	PRODUCT_NAME_ALREADY_EXISTS(1028, "A product with the same name already exists."),
	INVALID_EVENT_ID(1029, "Invalid eventid"), DUPLICATE_JOB_TITLE(1030, "Duplicate Job Title"),
	DUPLICATE_INTERNSHIP_TITLE(1031, "Duplicate Internship Title"), INVALID_USER(1032, "User Already Requested"),
	DUPLICATE_PROJECT_NAME(1033, "Project Name Already exsist for this service"),
	DUPLICATE_DATA(1034, "In-charge user is already assigned to another hub"),
	DUPLICATE_MILESTONE(1035, "Milestone Already exists for this project"),

	PAN_DUPLICATE_EXISTS(100,
			"This PAN has been used multiple times, please contact administrator to proceed. Please reach us at tech@startuptn.in or 155343 or +914422256789"),
	STARTUPINDIA_NOTREACHABLE(100,
			"Unable to reach StartupIndia Server at the moment, please try again later. Please reach us at tech@startuptn.in or 155343 or +914422256789"),
	INVALID_FILE_NAME(100, "Invalid File Name"), INVALID_TEMPLATE(100, "Notification Template Not Found"),
	RECORD_NOT_FOUND(100, "OAuth User Not Found"),
	INVALID_ROLE(100, "Invalid Role, Please reach us at tech@startuptn.in or 155343 or +914422256789"),
	INVALID_PERSONA(100, "Invalid Persona, Please reach us at tech@startuptn.in or 155343 or +914422256789"),
	INVALID_ID(100, "Invalid ID. Please reach us at tech@startuptn.in or 155343 or +914422256789"),
	INVALID_PERSONAV2(100,
			"Unable to set your persona type, Please reach us at tech@startuptn.in or 155343 or +914422256789"),
	INVALID_ROLEV2(100,
			"Unable to set your profile type, Please reach us at tech@startuptn.in or 155343 or +914422256789"),
	EMAIL_PROFILE_ALREADY_EXISTS(100,
			"Email is already associated with an registered account, Please reach us at tech@startuptn.in or 155343 or +914422256789"),
	REGISTRATION_FAILED(100, "Registration not completed, please close and open browser then try again"),
	INACTIVE_USER(100, "Account Deactivated"), USER_VERIFIED(100, "Account Already Verified, Kindly Login"),
	INVALID_USERID(100, "Invalid User ID. Please reach us at tech@startuptn.in or 155343 or +914422256789"),
	DUPLICATE_MEMBER(100, "Duplicate Member, Please reach us at tech@startuptn.in or 155343 or +914422256789"),
	INVALID_AADHAR(100, "Invalid Aadhar"), FAILED(100, "Failed"),
	INVALID_SMARTCARDPRICE_RANGE(100, "Invalid Smartcard Price Range"),
	MEMBER_ALREADY_EXIST(100, "Member already exists in Event Management & Coordination team"),
	DUPLICATE_HUB_NAME(101, "DUPLICATE HUB NAME"), DATA_NOT_FOUND(102, "DATA NOT FOUND"),
	INVALID_INCHARGE(100, "Invalid Incharge");

	private final String errorMessage;
	private final int errorCode;

	ErrorEnum(int errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return this.errorCode;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}
}