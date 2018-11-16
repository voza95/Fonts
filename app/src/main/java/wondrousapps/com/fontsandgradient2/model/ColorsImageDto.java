package wondrousapps.com.fontsandgradient2.model;

import android.os.Parcel;
import android.os.Parcelable;


public class ColorsImageDto implements Parcelable {
    public String title;
    public int resourceId;
    public String path;

    public ColorsImageDto(String title, int resourceId, String path) {
        this.title = title;
        this.resourceId = resourceId;
        this.path = path;
    }

    protected ColorsImageDto(Parcel in) {
        title = in.readString();
        path = in.readString();
        resourceId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(path);
        dest.writeInt(resourceId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ColorsImageDto> CREATOR = new Creator<ColorsImageDto>() {
        @Override
        public ColorsImageDto createFromParcel(Parcel in) {
            return new ColorsImageDto(in);
        }

        @Override
        public ColorsImageDto[] newArray(int size) {
            return new ColorsImageDto[size];
        }
    };
}
