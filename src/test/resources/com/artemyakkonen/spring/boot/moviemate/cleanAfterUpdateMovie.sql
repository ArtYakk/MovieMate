UPDATE public.movies
SET description='Before test', director='BeforeDirector', genre='BeforeGenre'
  , title='BeforeTitle', year=666, added_by='beforeAddedBy'
WHERE id=2;