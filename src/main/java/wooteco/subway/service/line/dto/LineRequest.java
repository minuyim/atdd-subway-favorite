package wooteco.subway.service.line.dto;

import wooteco.subway.domain.line.Line;

import java.time.LocalTime;

public class LineRequest {
	private String name;
	private LocalTime startTime;
	private LocalTime endTime;
	private int intervalTime;

	public LineRequest() {
	}

	public String getName() {
		return name;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public int getIntervalTime() {
		return intervalTime;
	}

	public Line toLine() {
		return Line.of(name, startTime, endTime, intervalTime);
	}
}
