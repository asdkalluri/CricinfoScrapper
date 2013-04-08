package stubs;

public class Match {

	String team1;
	String team2;
	String location;
	String series;

	public String getTeam1() {
		return team1;
	}
	public void setTeam1(String team1) {
		this.team1 = team1;
	}
	public String getTeam2() {
		return team2;
	}
	public void setTeam2(String team2) {
		this.team2 = team2;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}

	public void printMatchDetails()
	{
		System.out.println("Printing match details");
		System.out.println("Team1:"+this.getTeam1());
		System.out.println("Team2:"+this.getTeam2());
		System.out.println("Location:"+this.getLocation());
		System.out.println("Series:"+this.getSeries());

	}
}
