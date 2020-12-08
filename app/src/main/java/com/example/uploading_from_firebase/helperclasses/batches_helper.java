package com.example.uploading_from_firebase.helperclasses;

public class batches_helper {

    String batch_name;

    public batches_helper() {
    }

    private batches_helper(String batchname) {
        this.batch_name = batchname;
    }

    public String getBatchname() {
        return batch_name;
    }

    public void setBatchname(String batchname) {
        this.batch_name = batchname;
    }
}
