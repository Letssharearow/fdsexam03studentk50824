## Exam Template

This repository contains a template that can be used for the third assignment in module _Foundations of Distributed
Systems_ in Summer Semester 2022.

## Caching Strategy

### Dispatcher

no data -> no caching

### StudyTrip

collection: Because the service has paging, get requests on collection can change through post request. Therefore it's
not reasonable to cache them at all. Therefore the cache-control headers are set to: no-cache and no-store

single: Because private information is stored in a subressource it's posible to have the caching public. Changes of
studytrips are crucial, so the max-age can not be too long. Because changes occur rarely must-revalidate is in place,
since it saves operation time in the server. Once a study trip is over, it's rarely changed and also not too crucial,
that's when the max-age is set higher.

### Students

collection: Same reasoning as for Studytrips -> no-cache and no-store single: private information needs private caching.
Changes are crucial so the time is set to 30 seconds.

### Students of Studytrips

private information needs private caching. collection:  
can be stored, because it doesn't have pagination: max-age 30s Is rarely changed: must revalidate. same applies for
single.



