
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Administrator;
import domain.ExamClass;
import domain.Newspaper;

public class ExamClassForm {

	private int	id;
	private String	ticker;
	private String	title;
	private String	description;
	private Date	moment;
	private Integer	gauge;
	private boolean	draftMode;			
	private Newspaper newspaper;
	private Administrator admin;

	//Constructors -------------------------

	public ExamClassForm() {
		super();
		this.id = 0;
	}

	public ExamClassForm(ExamClass examClass) {
		super();
		this.id = examClass.getId();
	}

	//Getters and Setters -------------------------

		public Integer getId() {
		return this.id;
	}

	public String getTicker() {
		return ticker;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	@Size(min = 5, max = 32)	
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	@Size(min = 5, max = 32)	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	public Integer getGauge() {
		return gauge;
	}

	public void setGauge(Integer gauge) {
		this.gauge = gauge;
	}

	public boolean isDraftMode() {
		return draftMode;
	}

	public void setDraftMode(boolean draftMode) {
		this.draftMode = draftMode;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public Newspaper getNewspaper() {
		return newspaper;
	}

	public void setNewspaper(Newspaper newspaper) {
		this.newspaper = newspaper;
	}

	@NotNull
	public Administrator getAdmin() {
		return admin;
	}

	public void setAdmin(Administrator admin) {
		this.admin = admin;
	}

}
