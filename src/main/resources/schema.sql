CREATE TABLE IF NOT EXISTS FLORES_ENTITI (
                                    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
                                    FloresName VARCHAR(255) NOT NULL,
                                    FloresColor VARCHAR(50) NOT NULL,
                                    Tamano VARCHAR NOT NULL
);
