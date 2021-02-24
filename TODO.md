Welcome to Swapi App

First Kotlin, MVVP, and retrofit project, based on Star Wars API 
https://swapi.dev/

The app is pretty straight forward, you will see that you can:
 * Navigate to People, Planets and Species, from the left side navigation menu.
 * Scroll through the aforementioned data objects using the previous/next buttons, as well as
 performing a text search on those fragments.
 * Navigate to and between detail pages for people, species, planets and starships.
I made it pleasant to browse but without aspiration of UI expertize. Production-ready design would
need more time and attention to detail. Dark Theme is a little closer to Philo's color scheme.

Vehicles and Films are not implemented, to reduce the project scope somewhat, and frankly not to
return an even bigger app! They wouldn't be hard at all to add though, given another day.
I also didn't opt to do anything with creation/update dates.

A few notes on implementation:

I implemented the full MVVP model, including a few slightly extraneous classes that are meant to
mirror a full databased architecture (Repositoris, DAOs, SwapiRetrofitHelper that would be the
"database"). This should make for a clean architecture regardless of how many data sources we use,
and facilitate unit testing using dependency injection already setup in the Injector class.

Views themselves are resilient to activity change state and backstack rehydration, thanks to the
ViewModel architecture, but the fragments used in the NavController don't seem to be. I have spent
some time investigating this and still need to look at it more. As a consequence you will see
loading when flipping the screen and using the backstack, especially the lists. Caching works too
well see it in other places :) Definitely needs to be figured out though.

After a cursory look at all the class in the stack (Data model object, DAO, Repository, Factories,
Viewmodel), you might want to have a look at a couple of fragments, like PeopleFragment (list) and
PeopleDetailsFragment. Other fragments are mostly functioning the exact same as those.

I took a look at the Android SDK available but I wanted to make it myself in Kotlin.
Given time and a bigger scope project I would have looked into this a lot more.

Some high level aspects of every codebase could help a little more love. Namely unit testing, logs,
error handling, and metrics. I have added most of those in the TO-DO list below.

A few classes could use a parent. Serializable objects could have one and expose a name, url,
creation date, modification date. This would let me create a default list adapter as well, but
wasn't primordial for this version.
I would also consider a more complex framework, in which a templated item type could be linked to
views and have dependencies declared, so that creating new screens for new item types would be even
faster and generic. This would border on over-engineering possibly, would need to make a case for it
based on complexity and predicted future and scope of the project.

I am not a big fan of the JSON schema returning full item Url instead of item IDs. This caused me to
write somewhat goofy code using those instead of parsing the IDs out of them instead. I didn't feel
like parsing IDs would have solved much, instead introduced steps, possible bugs, just to palliate
the issue, not solve it.

I gave a thought to adding tree structure to the objects, so that a People object would contain a
fetched and parsed list of Vehicles and Starships, for example. This didn't seem necessary, and
would definitely add a lot of complexity and overhead. Given different project requirements, it
could on the other hand be a viable architecture.

Caching:
I started playing with memory caching. It was supposed to be for a couple classes, but since the
data set is so small and caching is so efficient, I ended up doing it for all. Given a larger
project, we would for sure spend a lot more time thinking of elaborate solutions there, including a
bespoke memory caching system and possibly local database caching.

Similarly, pre-loading could be a benefit to a larger product. Starting to load data connected to
the fragment currently being browsed, before the user click on one of the links to other pages.

### Todo

- [ ] Unit Testing
  - [x] Setup dependency injection
- [ ] More debug logs
- [ ] Metrics (number of requests, speed of requests, errors, fragment displays, searches...)
- [ ] Save state on NavController fragments. Should viewmodel be used for those?
- [ ] Text Alignment "ViewStart" doesn't seem to do a lot on most detail pages. Figure it out.
- [ ] Retry framework (auto) and UX (manual) for retrying failed requests
- [ ] More exhausting error handling
- [ ] Figure out the ideal depth of the backstack when diving into details / how to go back top.
- [ ] Implement a translation framework (mostly handled though several string.xml)
- [ ] Give more attention to accessibility (contentDescription and other navigation helpers)
- [ ] Refactor memory caching, consider database caching.
- [ ] Create a parent class for JSON objects
- [ ] Create a parent class for Simple Name Adapters
- [ ] Implement Vehicles
  - [ ] Vehicle Search fragment and pagination
  - [ ] Vehicle details fragment
  - [ ] Navigation to and from Vehicle fragments
- [ ] Implement Films
  - [ ] Film Search fragment and pagination
  - [ ] Film details fragment
  - [ ] Navigation to and from Film fragments
- [ ] Starship Search fragment and pagination
  - [ ] Navigation to and from Starship Search fragments
- [ ] Figure out how/why nulls are serialized from Swapi API
- [ ] Investigate the multiple species "feature" for a people object

### In Progress

- [ ] Figure out why some fields are still center aligned in detail views (Planets, Species, etc.)
- [ ] Extract the remaining hard coded strings from the code into resources

### Done ✓

- [x] Implement People Search fragment
  - [x] Implement People Pagination
- [x] Implement People Details fragment
- [x] Implement Planet Search fragment
  - [x] Implement Planet Pagination
- [x] Implement Planet Details fragment
- [x] Implement Species Search fragment
  - [x] Implement Species Pagination
- [x] Implement Species Details fragment
- [x] Implement Starship Details fragment
- [x] Implement Navigation between all of the fragments