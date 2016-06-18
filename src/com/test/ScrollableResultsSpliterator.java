package com.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by ash on 18/06/16.
 */

public class ScrollableResultsSpliterator<T> extends FixedBatchSpliteratorBase<T> {
    private final ResultSet results;
    private boolean closed;


    public ScrollableResultsSpliterator(Class<T> clazz,int batchSize, ResultSet results)
    {
        super(ORDERED | NONNULL, batchSize);
        if (results == null)
            throw new NullPointerException("ScrollableResults must not be null");
        this.results = results;
    }

    @SuppressWarnings("unchecked")
    @Override public boolean tryAdvance(Consumer<? super T> action) {
        if (closed) return false;

        // try to get the next row
        try {
            if (!results.next()) {
                close();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

            // if here then there is still data so get the column 1 and send it back
            String r = null;
            try {
                r = results.getString(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // send the data
            action.accept((T)r);

        return true;
    }

    // when the stream is closed
    public void close() {
        if (!closed) {
            try {
                results.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                closed = true;
            }

        }
    }

    public static <T> Stream<T> resultStream(
            Class<T> clazz, int batchSize, ResultSet query)
    {
        return resultStream(new ScrollableResultsSpliterator<T>(clazz, batchSize, query));
    }

    @SuppressWarnings("unchecked")
    public static <T> Stream<T> resultStream(
            ScrollableResultsSpliterator<T> spliterator)
    {
        return StreamSupport.stream(spliterator, false)
                .onClose(spliterator::close);
    }


}