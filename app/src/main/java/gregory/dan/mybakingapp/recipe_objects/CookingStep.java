package gregory.dan.mybakingapp.recipe_objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Daniel Gregory on 30/07/2018.
 */
public class CookingStep implements Parcelable {

    private int stepNumber;
    private String shortDescription;
    private String description;
    private String videoUrl;
    private String thumbnailUrl;

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public CookingStep(int stepNumber, String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        this.stepNumber = stepNumber;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    protected CookingStep(Parcel in) {
        stepNumber = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoUrl = in.readString();
        thumbnailUrl = in.readString();
    }

    public static final Creator<CookingStep> CREATOR = new Creator<CookingStep>() {
        @Override
        public CookingStep createFromParcel(Parcel in) {
            return new CookingStep(in);
        }

        @Override
        public CookingStep[] newArray(int size) {
            return new CookingStep[size];
        }
    };

    public int getStepNumber() {
        return stepNumber;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(stepNumber);
        parcel.writeString(shortDescription);
        parcel.writeString(description);
        parcel.writeString(videoUrl);
        parcel.writeString(thumbnailUrl);
    }
}
