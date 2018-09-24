package cz.vabalcar.jbot.events;

import java.io.Serializable;

public class DataEventRaiser<T extends Serializable> extends DataListenerImpl<T> {
	
    private final DataProvider<T> dataProvider;
    
    public DataEventRaiser(DataProvider<T> dataProvider) {
        super(dataProvider.getProvidedDataType());
        this.dataProvider = dataProvider;
    }

    @Override
    public boolean processDataEvent(DataEvent<? extends T> event) {
        return dataProvider.raiseDataEvent(event);
    }

    @Override
    public void close() throws Exception {
        dataProvider.close();
    }

}
