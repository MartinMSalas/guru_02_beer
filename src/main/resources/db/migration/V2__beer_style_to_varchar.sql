ALTER TABLE beer
    MODIFY beer_style VARCHAR(100) NOT NULL;

CREATE INDEX idx_beer_beer_style ON beer(beer_style);
