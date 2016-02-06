package serverdbclasses;

/**
 * Created by filippos on 5/2/2016.
 */
public class Result {
    private int jobid;
    private String raw_text;

    public Result(int jobid, String output) {
        this.jobid = jobid;
        this.raw_text = output;
    }

    public Result() {
    }

    public int getJobid() {
        return jobid;
    }

    public void setJobid(int jobid) {
        this.jobid = jobid;
    }

    public String getRaw_text() {
        return raw_text;
    }

    public void setRaw_text(String raw_text) {
        this.raw_text = raw_text;
    }
}
