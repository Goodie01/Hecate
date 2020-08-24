package org.goodiemania.hecate;

import java.util.List;
import org.goodiemania.hecate.logs.Log;
import org.primefaces.model.LazyDataModel;

public class LazyLogsDataModel extends LazyDataModel<Log> {
    public LazyLogsDataModel(final List<Log> logsList) {
        setWrappedData(logsList);
    }
}
