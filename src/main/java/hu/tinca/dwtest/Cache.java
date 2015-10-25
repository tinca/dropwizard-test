package hu.tinca.dwtest;

/**
 *  A simple cache interface: what is put in remains there forever. <BR>
 *  In general: put methods put object into cache, update if exists one with that name.
 *  Get methods return object with that name, or null if none.
 */
public interface Cache {

    /**
     * Puts artist into cache, overwrites if exists.
     * @param artist
     *
     */
    void put(Artist artist);

    /**
     * Puts artists into cache, overwrites if exists.
     * @param artists
     */
    void put(Artists artists);

    /**
     * Get artist with this name, null if none.
     * @param name
     * @return
     */
    Artist getArtist(String name);

    Artists getArtists(String name);
}
