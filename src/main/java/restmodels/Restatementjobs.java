package restmodels;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

//@JsonFormat(shape=JsonFormat.Shape.ARRAY)
public class Restatementjobs {

	@JsonProperty
	List<Restatementjob> restatementjobs;

	public List<Restatementjob> getRestatementjobs() {
		return restatementjobs;
	}

	public void setRestatementjobs(List<Restatementjob> restatementjobs) {
		this.restatementjobs = restatementjobs;
	}

	@Override
	public String toString() {
		return "Restatementjobs [restatementjobs=" + restatementjobs + "]";
	}
	

}
