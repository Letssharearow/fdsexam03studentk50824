package de.fhws.fiw.fds.exam02.api.hypermedia.rel_types;

public interface IStudyTripStudentRelTypes
{
	String CREATE_STUDENT_OF_STUDY_TRIP = "createStudentOfStudyTrip";
	String GET_ALL_LINKED_STUDENTS = "getAllLinkedStudents";
	String GET_ALL_STUDENTS = "getAllLinkableStudents";
	String UPDATE_SINGLE_STUDENT = "updateStudentOfStudyTrip";
	String CREATE_LINK_FROM_STUDY_TRIP_TO_STUDENT = "linkStudentToStudyTrip";
	String DELETE_LINK_FROM_STUDY_TRIP_TO_STUDENT = "unlinkStudentFromStudyTrip";
	String GET_SINGLE_STUDENT = "getStudentOfStudyTrip";
}