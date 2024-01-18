package reactiveusersmicroservice.dal;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import reactiveusersmicroservice.data.UserEntity;
import reactor.core.publisher.Flux;

import java.util.Date;

public interface ReactiveUsersCrud extends ReactiveMongoRepository<UserEntity, String>{
	// TODO: add methods

	public Flux<UserEntity> findAllByName_Last (
			@Param("lastName") String lastName);


	//TODO: the service: (ByDomain)
	// public Flux<UserBoundary> getByDomain(String domain) {
	//		return this.ReactiveUsersCrud
	//			.findAllByEmailLike("*" +"@" + domain)
	//			.map(Converter::toBoundary);
	//	}
	public Flux<UserEntity> findAllByEmailLike (
			@Param("pattern") String pattern);


	//TODO: check and validate
	// the service: (ByMinAge)
	// public Flux<UserBoundary> getByMinAge(int minAge) {
	//		return this.ReactiveUsersCrud
	//			.findAllByBirthdateBefore(minBirthdate)
	//			.map(Converter::toBoundary);
	//	}
	// import java.util.Calendar;
	// Calendar calendar = Calendar.getInstance();
	// calendar.add(Calendar.YEAR, -18); // Set the cutoff date to be 18 years ago
	// Date minBirthdate = calendar.getTime();

	// check if the birthdate should be a date or int
	public Flux<UserEntity> findAllByBirthdateBefore (
			@Param("birthdate") int birthdate);

}
