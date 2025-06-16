import jakarta.annotation.security.RolesAllowed
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import org.acme.dto.DashboardSummary
import org.acme.repository.DashboardRepository

@Path("/dashboard")
class DashboardResource(private val dashboardRepository: DashboardRepository) {

    @GET
    @RolesAllowed("ADMIN")
    fun getDashboardSummary(): DashboardSummary {
        return dashboardRepository.getSummary()
    }
}
