package ru.roznov.servlets_2.objects.requests;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractRequest implements Comparable<AbstractRequest> {
    private int driverId;
    private RequestType requestType;

    private int requestId;

    private static List<AbstractRequest> requestList = new ArrayList<>();


    public AbstractRequest(int driverId, RequestType requestType) {
        this.driverId = driverId;
        this.requestType = requestType;
        AbstractRequest.requestList.add(this);
        this.requestId = AbstractRequest.requestList.indexOf(this);
    }

    public int getDriverId() {
        return this.driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public RequestType getRequestType() {
        return this.requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public int getRequestId() {
        return this.requestId;
    }

    public void deleteRequest() {
        AbstractRequest.requestList.remove(this.requestId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractRequest)) return false;
        AbstractRequest other = (AbstractRequest) o;
        return this.getDriverId() == other.getDriverId() && this.getRequestType() == other.getRequestType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDriverId(), getRequestType());
    }

    @Override
    public int compareTo(AbstractRequest o) {
        return Integer.compare(o.getRequestId(), this.getRequestId());
    }
}
