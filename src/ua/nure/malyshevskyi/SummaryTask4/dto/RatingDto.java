package ua.nure.malyshevskyi.SummaryTask4.dto;

import java.util.List;

import ua.nure.malyshevskyi.SummaryTask4.dto.bean.RatingBean;
import ua.nure.malyshevskyi.SummaryTask4.exception.DBException;

public interface RatingDto {
	List<RatingBean> getRatingBeanByCourseId(long id) throws DBException;
}
