import jenkins.model.*
import hudson.security.*

def instance = Jenkins.getInstance()

// Create local users
def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount("admin","AdminPassword123!")
hudsonRealm.createAccount("ci-user","CiUserPassw0rd!")
instance.setSecurityRealm(hudsonRealm)

// Matrix-based security
def strategy = new GlobalMatrixAuthorizationStrategy()

strategy.add(Jenkins.ADMINISTER, "admin")   // admin rights
strategy.add(Jenkins.READ, "ci-user")       // limited rights
strategy.add(Item.READ, "ci-user")
strategy.add(Item.BUILD, "ci-user")
strategy.add(Run.READ, "ci-user")

// Disable anonymous access (no permissions granted to anonymous)
instance.setAuthorizationStrategy(strategy)
instance.save()
