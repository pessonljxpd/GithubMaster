package com.adark.gm.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shelly on 2017-3-13.
 */

public class Plan implements Parcelable {
    private String name;

    private int space;

    private int collaborators;

    private int private_repos;

    protected Plan(Parcel in) {
        name = in.readString();
        space = in.readInt();
        collaborators = in.readInt();
        private_repos = in.readInt();
    }

    public static final Creator<Plan> CREATOR = new Creator<Plan>() {
        @Override public Plan createFromParcel(Parcel in) {
            return new Plan(in);
        }

        @Override public Plan[] newArray(int size) {
            return new Plan[size];
        }
    };

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public int getSpace() {
        return this.space;
    }

    public void setCollaborators(int collaborators) {
        this.collaborators = collaborators;
    }

    public int getCollaborators() {
        return this.collaborators;
    }

    public void setPrivate_repos(int private_repos) {
        this.private_repos = private_repos;
    }

    public int getPrivate_repos() {
        return this.private_repos;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(space);
        dest.writeInt(collaborators);
        dest.writeInt(private_repos);
    }
}
