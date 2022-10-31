package me.designpattern.code.creational.builder.example;

import java.time.LocalDate;

public class TourDirector {
	private final TourPlanBuilder tourPlanBuilder;

	public TourDirector(TourPlanBuilder tourPlanBuilder) {
		this.tourPlanBuilder = tourPlanBuilder;
	}

	public TourPlan exampleTrip() {
		return tourPlanBuilder.title("example trip")
			.nightsAndDays(2,3)
			.startDate(LocalDate.of(2022, 10, 15))
			.whereToStay("fancy resort")
			.addPlan(0, "check in")
			.addPlan(1, "sleep")
			.getPlan();
	}
}
