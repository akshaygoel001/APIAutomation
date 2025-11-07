package pojo;

import java.util.List;

public class PostRequestBody {

    private String name;
    private String job;
    private List<String> languages;
    private List<CityRequest> city;
    private String updatedAt;

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityRequest> getCity() {
        return city;
    }

    public void setCity(List<CityRequest> city) {
        this.city = city;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
