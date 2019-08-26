package fhm.wi.team5.android_application.transfer;

/**
 * @author Jan Schönfeld
 */

public class RentalRequest {
    private long bikeId;
    private long ownerId;
    private long customerId;

    public RentalRequest() {
        super();
    }

    public RentalRequest(long bikeId, long ownerId, long customerId) {
        super();
        this.bikeId = bikeId;
        this.ownerId = ownerId;
        this.customerId = customerId;
    }

    public long getBikeId() {
        return bikeId;
    }

    public void setBikeId(Long bikeId) {
        this.bikeId = bikeId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}