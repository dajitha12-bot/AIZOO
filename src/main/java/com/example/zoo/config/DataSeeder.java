package com.example.zoo.config;

import com.example.zoo.entity.*;
import com.example.zoo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final AnimalRepository animalRepository;
    private final FoodRepository foodRepository;
    private final SpeciesKnowledgeRepository speciesKnowledgeRepository;
    private final SpeciesFoodRepository speciesFoodRepository;
    private final EnrichmentKnowledgeRepository enrichmentKnowledgeRepository;
    private final CareRuleRepository careRuleRepository;
    private final ZookeeperRepository zookeeperRepository;
    private final ZookeeperAvailabilityRepository zookeeperAvailabilityRepository;
    private final FoodAvailabilityRepository foodAvailabilityRepository;
    private final ObservationRepository observationRepository;

    public DataSeeder(AnimalRepository animalRepository,
                      FoodRepository foodRepository,
                      SpeciesKnowledgeRepository speciesKnowledgeRepository,
                      SpeciesFoodRepository speciesFoodRepository,
                      EnrichmentKnowledgeRepository enrichmentKnowledgeRepository,
                      CareRuleRepository careRuleRepository,
                      ZookeeperRepository zookeeperRepository,
                      ZookeeperAvailabilityRepository zookeeperAvailabilityRepository,
                      FoodAvailabilityRepository foodAvailabilityRepository,
                      ObservationRepository observationRepository) {
        this.animalRepository = animalRepository;
        this.foodRepository = foodRepository;
        this.speciesKnowledgeRepository = speciesKnowledgeRepository;
        this.speciesFoodRepository = speciesFoodRepository;
        this.enrichmentKnowledgeRepository = enrichmentKnowledgeRepository;
        this.careRuleRepository = careRuleRepository;
        this.zookeeperRepository = zookeeperRepository;
        this.zookeeperAvailabilityRepository = zookeeperAvailabilityRepository;
        this.foodAvailabilityRepository = foodAvailabilityRepository;
        this.observationRepository = observationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            ensureLocalImagesExist();
        } catch (Throwable t) {
            System.out.println("Notice: Dynamic image generation skipped in cloud environment (" + t.getMessage() + "). Pre-packaged static images will be used.");
        }

        if (animalRepository.count() == 0) {
            seedSpeciesKnowledge();
            seedFoods();
            seedZookeepers();
            seedAnimals();
            seedCareRules();
            seedObservations();
            System.out.println(">>> SQLite zoo.db database successfully initialized with demo data!");
        }
    }

    private void ensureLocalImagesExist() {
        String[] speciesList = {"elephant", "lion", "giraffe", "zebra", "tiger", "deer", "monkey", "bear", "penguin", "rhino", "hippo", "parrot", "crocodile"};
        Color[] colors = {
                new Color(128, 128, 128), new Color(218, 165, 32), new Color(255, 140, 0),
                new Color(112, 128, 144), new Color(255, 69, 0), new Color(139, 69, 19),
                new Color(205, 133, 63), new Color(101, 67, 33), new Color(47, 79, 79),
                new Color(105, 105, 105), new Color(119, 136, 153), new Color(34, 139, 34),
                new Color(85, 107, 47)
        };

        String targetDir = "src/main/resources/static/images/animals";
        File dir = new File(targetDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        for (int i = 0; i < speciesList.length; i++) {
            String name = speciesList[i];
            File file = new File(dir, name + ".png");
            if (!file.exists()) {
                generateAnimalImage(file, name, colors[i % colors.length]);
            }
        }
    }

    private void generateAnimalImage(File file, String animalName, Color bgColor) {
        try {
            int width = 300;
            int height = 300;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            // Card background
            GradientPaint gp = new GradientPaint(0, 0, bgColor, width, height, Color.WHITE);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, width, height);

            // Inner circle preview
            g2d.setColor(Color.WHITE);
            g2d.fillOval(50, 30, 200, 200);

            g2d.setColor(new Color(30, 58, 138));
            g2d.setStroke(new BasicStroke(4));
            g2d.drawOval(50, 30, 200, 200);

            // Text Label inside circle
            g2d.setFont(new Font("SansSerif", Font.BOLD, 22));
            FontMetrics fm = g2d.getFontMetrics();
            String title = animalName.toUpperCase();
            int titleWidth = fm.stringWidth(title);
            g2d.drawString(title, (width - titleWidth) / 2, 140);

            g2d.setFont(new Font("SansSerif", Font.PLAIN, 14));
            String sub = "SPECIES ICON";
            int subWidth = g2d.getFontMetrics().stringWidth(sub);
            g2d.setColor(Color.GRAY);
            g2d.drawString(sub, (width - subWidth) / 2, 170);

            // Outer border
            g2d.setColor(new Color(30, 58, 138));
            g2d.drawRect(2, 2, width - 4, height - 4);

            g2d.dispose();
            ImageIO.write(image, "PNG", file);
        } catch (Throwable e) {
            System.err.println("Notice: Dynamic image generation skipped for " + animalName);
        }
    }

    private void seedSpeciesKnowledge() {
        List<SpeciesKnowledge> species = Arrays.asList(
                new SpeciesKnowledge("Elephant", "Large herbivorous mammal requiring high forage intake.", 3),
                new SpeciesKnowledge("Lion", "Apex carnivore requiring high protein raw meat diet.", 2),
                new SpeciesKnowledge("Giraffe", "Tall herbivore browsing upper foliage and acacia.", 2),
                new SpeciesKnowledge("Zebra", "Grazing herbivore requiring fibrous roughage diet.", 2),
                new SpeciesKnowledge("Tiger", "Solitary carnivore needing fresh meat diet.", 2),
                new SpeciesKnowledge("Deer", "Agile herbivore consuming grass, hay, and root veggies.", 2),
                new SpeciesKnowledge("Monkey", "Omnivorous primate needing fruits, veggies, and forage.", 3),
                new SpeciesKnowledge("Bear", "Large omnivore consuming meat, fruits, and roots.", 2),
                new SpeciesKnowledge("Penguin", "Aquatic bird requiring fresh fish/meat portion.", 2),
                new SpeciesKnowledge("Rhino", "Heavy grazer requiring dense hay and grass.", 2),
                new SpeciesKnowledge("Hippo", "Semi-aquatic grazer consuming massive night grass.", 2),
                new SpeciesKnowledge("Parrot", "Tropical bird needing fruits, seeds, and nuts.", 3),
                new SpeciesKnowledge("Crocodile", "Aquatic reptile fed scheduled raw meat portions.", 1)
        );
        speciesKnowledgeRepository.saveAll(species);

        // Species Foods
        List<SpeciesFood> foods = Arrays.asList(
                new SpeciesFood("Elephant", "Grass", 25.0, true),
                new SpeciesFood("Elephant", "Hay", 15.0, false),
                new SpeciesFood("Elephant", "Banana", 5.0, true),
                new SpeciesFood("Elephant", "Carrot", 5.0, false),

                new SpeciesFood("Lion", "Meat", 15.0, true),

                new SpeciesFood("Giraffe", "Hay", 12.0, true),
                new SpeciesFood("Giraffe", "Grass", 8.0, false),
                new SpeciesFood("Giraffe", "Carrot", 3.0, false),

                new SpeciesFood("Zebra", "Grass", 12.0, true),
                new SpeciesFood("Zebra", "Hay", 6.0, false),

                new SpeciesFood("Tiger", "Meat", 14.0, true),
                new SpeciesFood("Deer", "Grass", 8.0, true),
                new SpeciesFood("Deer", "Hay", 4.0, false),
                new SpeciesFood("Monkey", "Banana", 2.0, true),
                new SpeciesFood("Monkey", "Apple", 2.0, false),
                new SpeciesFood("Bear", "Meat", 10.0, true),
                new SpeciesFood("Bear", "Banana", 4.0, false),
                new SpeciesFood("Penguin", "Meat", 3.0, true),
                new SpeciesFood("Rhino", "Grass", 20.0, true),
                new SpeciesFood("Hippo", "Grass", 22.0, true),
                new SpeciesFood("Parrot", "Banana", 0.5, true),
                new SpeciesFood("Parrot", "Apple", 0.5, false),
                new SpeciesFood("Crocodile", "Meat", 8.0, true)
        );
        speciesFoodRepository.saveAll(foods);

        // Enrichment Knowledge
        List<EnrichmentKnowledge> enrichments = Arrays.asList(
                new EnrichmentKnowledge("Elephant", "Water Play", "Provide mud bath & water hose spray for skin cooling."),
                new EnrichmentKnowledge("Elephant", "Foraging", "Hide bananas and hay inside hanging burlap sacks."),
                new EnrichmentKnowledge("Elephant", "Puzzle Feeding", "Log puzzle feeder containing carrots."),

                new EnrichmentKnowledge("Lion", "Scent Tracking", "Scatter cinnamon and herb scents across enclosure."),
                new EnrichmentKnowledge("Lion", "Ice Treat", "Provide large blood-ice block for stimulation."),

                new EnrichmentKnowledge("Giraffe", "High Feeder Toy", "Elevate browse basket to 4 meters height."),
                new EnrichmentKnowledge("Zebra", "Pasture Grazing", "Open rotatorial meadow for free grazing."),
                new EnrichmentKnowledge("Tiger", "Water Pool", "Fill deep pool with floating balls for hunting simulation."),
                new EnrichmentKnowledge("Monkey", "Climbing Ropes", "Install dynamic climbing ropes and hidden food boxes.")
        );
        enrichmentKnowledgeRepository.saveAll(enrichments);
    }

    private void seedFoods() {
        List<Food> foodList = Arrays.asList(
                new Food("Grass", 150.0, "kg", true),
                new Food("Hay", 80.0, "kg", true),
                new Food("Banana", 10.0, "kg", true),
                new Food("Apple", 0.0, "kg", false),
                new Food("Carrot", 40.0, "kg", true),
                new Food("Meat", 50.0, "kg", true),
                new Food("Sugarcane", 30.0, "kg", true)
        );
        foodRepository.saveAll(foodList);

        for (Food f : foodList) {
            foodAvailabilityRepository.save(new FoodAvailability(f.getName(), f.getTotalQuantity(), f.getUnit(), f.getIsAvailable(), null));
        }
    }

    private void seedZookeepers() {
        List<Zookeeper> keepers = Arrays.asList(
                new Zookeeper("Zookeeper A", "08:00 AM", "04:00 PM", true),
                new Zookeeper("Zookeeper B", "09:00 AM", "05:00 PM", true),
                new Zookeeper("Zookeeper C", "10:00 AM", "06:00 PM", true)
        );
        zookeeperRepository.saveAll(keepers);

        for (Zookeeper zk : keepers) {
            zookeeperAvailabilityRepository.save(new ZookeeperAvailability(zk.getName(), zk.getShiftStart(), zk.getShiftEnd(), zk.getIsAvailable()));
        }
    }

    private void seedAnimals() {
        List<Animal> animals = Arrays.asList(
                new Animal("E101", "Aruna", "Elephant", 12, "Female", "Banana", "Healthy elephant, loves water play.", "/images/animals/elephant.png"),
                new Animal("E102", "Bala", "Elephant", 10, "Male", "Grass", "Active young male elephant.", "/images/animals/elephant.png"),
                new Animal("L101", "Simba", "Lion", 8, "Male", "Meat", "Pride leader, active during morning.", "/images/animals/lion.png"),
                new Animal("G101", "Maya", "Giraffe", 7, "Female", "Hay", "Prefers high feeder ropes.", "/images/animals/giraffe.png"),
                new Animal("Z101", "Zara", "Zebra", 6, "Female", "Grass", "Enjoys pasture grazing with group.", "/images/animals/zebra.png")
        );
        animalRepository.saveAll(animals);
    }

    private void seedCareRules() {
        List<CareRule> rules = Arrays.asList(
                new CareRule("Reduced Appetite Warning", "foodIntake == 'Low'", "Monitor closely. Consider veterinary review if persistent."),
                new CareRule("Lethargy & Dehydration Check", "foodIntake == 'Low' AND activityLevel == 'Low'", "Immediate zookeeper observation required. Check hydration levels and offer palatable preferences."),
                new CareRule("Optimal Health", "foodIntake == 'Normal' AND activityLevel == 'Normal'", "Standard care and feeding routine maintained.")
        );
        careRuleRepository.saveAll(rules);
    }

    private void seedObservations() {
        Observation obs = new Observation("E101", "Aruna", "Low", "Normal", "Low", "Aruna ate less food today.");
        obs.setAiPatternDetected("Monitor");
        observationRepository.save(obs);
    }
}
