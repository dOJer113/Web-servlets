package ru.roznov.servlets_2.objects.requests;

public class EntryRequest extends AbstractRequest {
    private int storeId;

    public EntryRequest(int driverId, int storeId) {
        super(driverId, RequestType.ENTRY, storeId);
        this.storeId = storeId;
    }

    public int getStoreId() {
        return this.storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }
}
