package com.adark.gm.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shelly on 2017-3-13.
 */

public class User implements Parcelable {
    private String login;

    private int id;

    private String avatar_url;

    private String gravatar_id;

    private String url;

    private String html_url;

    private String followers_url;

    private String following_url;

    private String gists_url;

    private String starred_url;

    private String subscriptions_url;

    private String organizations_url;

    private String repos_url;

    private String events_url;

    private String received_events_url;

    private String type;

    private boolean site_admin;

    private String name;

    private String company;

    private String blog;

    private String location;

    private String email;

    private String hireable;

    private String bio;

    private int public_repos;

    private int public_gists;

    private int followers;

    private int following;

    private String created_at;

    private String updated_at;

    private int private_gists;

    private int total_private_repos;

    private int owned_private_repos;

    private int disk_usage;

    private int collaborators;

    private boolean two_factor_authentication;

    private Plan plan;

    protected User(Parcel in) {
        login = in.readString();
        id = in.readInt();
        avatar_url = in.readString();
        gravatar_id = in.readString();
        url = in.readString();
        html_url = in.readString();
        followers_url = in.readString();
        following_url = in.readString();
        gists_url = in.readString();
        starred_url = in.readString();
        subscriptions_url = in.readString();
        organizations_url = in.readString();
        repos_url = in.readString();
        events_url = in.readString();
        received_events_url = in.readString();
        type = in.readString();
        site_admin = in.readByte() != 0;
        name = in.readString();
        company = in.readString();
        blog = in.readString();
        location = in.readString();
        email = in.readString();
        hireable = in.readString();
        bio = in.readString();
        public_repos = in.readInt();
        public_gists = in.readInt();
        followers = in.readInt();
        following = in.readInt();
        created_at = in.readString();
        updated_at = in.readString();
        private_gists = in.readInt();
        total_private_repos = in.readInt();
        owned_private_repos = in.readInt();
        disk_usage = in.readInt();
        collaborators = in.readInt();
        two_factor_authentication = in.readByte() != 0;
        plan = in.readParcelable(Plan.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override public User[] newArray(int size) {
            return new User[size];
        }
    };

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return this.login;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getAvatar_url() {
        return this.avatar_url;
    }

    public void setGravatar_id(String gravatar_id) {
        this.gravatar_id = gravatar_id;
    }

    public String getGravatar_id() {
        return this.gravatar_id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getHtml_url() {
        return this.html_url;
    }

    public void setFollowers_url(String followers_url) {
        this.followers_url = followers_url;
    }

    public String getFollowers_url() {
        return this.followers_url;
    }

    public void setFollowing_url(String following_url) {
        this.following_url = following_url;
    }

    public String getFollowing_url() {
        return this.following_url;
    }

    public void setGists_url(String gists_url) {
        this.gists_url = gists_url;
    }

    public String getGists_url() {
        return this.gists_url;
    }

    public void setStarred_url(String starred_url) {
        this.starred_url = starred_url;
    }

    public String getStarred_url() {
        return this.starred_url;
    }

    public void setSubscriptions_url(String subscriptions_url) {
        this.subscriptions_url = subscriptions_url;
    }

    public String getSubscriptions_url() {
        return this.subscriptions_url;
    }

    public void setOrganizations_url(String organizations_url) {
        this.organizations_url = organizations_url;
    }

    public String getOrganizations_url() {
        return this.organizations_url;
    }

    public void setRepos_url(String repos_url) {
        this.repos_url = repos_url;
    }

    public String getRepos_url() {
        return this.repos_url;
    }

    public void setEvents_url(String events_url) {
        this.events_url = events_url;
    }

    public String getEvents_url() {
        return this.events_url;
    }

    public void setReceived_events_url(String received_events_url) {
        this.received_events_url = received_events_url;
    }

    public String getReceived_events_url() {
        return this.received_events_url;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setSite_admin(boolean site_admin) {
        this.site_admin = site_admin;
    }

    public boolean getSite_admin() {
        return this.site_admin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getBlog() {
        return this.blog;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return this.location;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setHireable(String hireable) {
        this.hireable = hireable;
    }

    public String getHireable() {
        return this.hireable;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBio() {
        return this.bio;
    }

    public void setPublic_repos(int public_repos) {
        this.public_repos = public_repos;
    }

    public int getPublic_repos() {
        return this.public_repos;
    }

    public void setPublic_gists(int public_gists) {
        this.public_gists = public_gists;
    }

    public int getPublic_gists() {
        return this.public_gists;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowers() {
        return this.followers;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getFollowing() {
        return this.following;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUpdated_at() {
        return this.updated_at;
    }

    public void setPrivate_gists(int private_gists) {
        this.private_gists = private_gists;
    }

    public int getPrivate_gists() {
        return this.private_gists;
    }

    public void setTotal_private_repos(int total_private_repos) {
        this.total_private_repos = total_private_repos;
    }

    public int getTotal_private_repos() {
        return this.total_private_repos;
    }

    public void setOwned_private_repos(int owned_private_repos) {
        this.owned_private_repos = owned_private_repos;
    }

    public int getOwned_private_repos() {
        return this.owned_private_repos;
    }

    public void setDisk_usage(int disk_usage) {
        this.disk_usage = disk_usage;
    }

    public int getDisk_usage() {
        return this.disk_usage;
    }

    public void setCollaborators(int collaborators) {
        this.collaborators = collaborators;
    }

    public int getCollaborators() {
        return this.collaborators;
    }

    public void setTwo_factor_authentication(boolean two_factor_authentication) {
        this.two_factor_authentication = two_factor_authentication;
    }

    public boolean getTwo_factor_authentication() {
        return this.two_factor_authentication;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Plan getPlan() {
        return this.plan;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(login);
        dest.writeInt(id);
        dest.writeString(avatar_url);
        dest.writeString(gravatar_id);
        dest.writeString(url);
        dest.writeString(html_url);
        dest.writeString(followers_url);
        dest.writeString(following_url);
        dest.writeString(gists_url);
        dest.writeString(starred_url);
        dest.writeString(subscriptions_url);
        dest.writeString(organizations_url);
        dest.writeString(repos_url);
        dest.writeString(events_url);
        dest.writeString(received_events_url);
        dest.writeString(type);
        dest.writeByte((byte) (site_admin ? 1 : 0));
        dest.writeString(name);
        dest.writeString(company);
        dest.writeString(blog);
        dest.writeString(location);
        dest.writeString(email);
        dest.writeString(hireable);
        dest.writeString(bio);
        dest.writeInt(public_repos);
        dest.writeInt(public_gists);
        dest.writeInt(followers);
        dest.writeInt(following);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeInt(private_gists);
        dest.writeInt(total_private_repos);
        dest.writeInt(owned_private_repos);
        dest.writeInt(disk_usage);
        dest.writeInt(collaborators);
        dest.writeByte((byte) (two_factor_authentication ? 1 : 0));
        dest.writeParcelable(plan, flags);
    }
}

